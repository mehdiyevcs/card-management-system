package az.company.cardorder.service;

import az.company.cardorder.constant.RabbitMQConstants;
import az.company.cardorder.domain.enumeration.CardOrderOperationType;
import az.company.cardorder.domain.enumeration.OrderStatus;
import az.company.cardorder.dto.CardOrderDto;
import az.company.cardorder.error.exception.InvalidInputException;
import az.company.cardorder.error.exception.NotFoundException;
import az.company.cardorder.error.validation.ValidationMessage;
import az.company.cardorder.mapper.CardOrderMapper;
import az.company.cardorder.messaging.MessageProducer;
import az.company.cardorder.messaging.event.CardOrderEvent;
import az.company.cardorder.model.CreateCardOrderRequest;
import az.company.cardorder.repository.CardOrderRepository;
import az.company.cardorder.util.ConvertUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author MehdiyevCS on 22.08.21
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CardOrderService {

    //UserId will be extracted from ContextHolder
    private static final Long USER_ID1 = 12345L;

    private final CardOrderRepository cardOrderRepository;
    private final CardOrderMapper cardOrderMapper;
    private final CardOrderOperationService cardOrderOperationService;
    private final MessageProducer messageProducer;

    public List<CardOrderDto> getCardOrders() {
        return cardOrderRepository.findAllByUserId(USER_ID1)
                .stream()
                .map(cardOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<CardOrderDto> getCardOrder(Long id) {
        return cardOrderRepository.findByIdAndUserId(id, USER_ID1).map(cardOrderMapper::toDto);
    }

    public CardOrderDto save(CardOrderDto cardOrderDto) {
        var cardOrder = cardOrderMapper.toEntity(cardOrderDto);
        cardOrder.setUserId(USER_ID1);
        cardOrder = cardOrderRepository.save(cardOrder);
        return cardOrderMapper.toDto(cardOrder);
    }

    public CardOrderDto createCardOrder(CreateCardOrderRequest createCardOrderRequest) {

        checkPeriod(createCardOrderRequest.getPeriod());

        var cardOrderDto = CardOrderDto.builder()
                .cardHolderFullName(createCardOrderRequest.getCardHolderFullName())
                .cardHolderPin(createCardOrderRequest.getCardHolderPin())
                .cardType(createCardOrderRequest.getCardType())
                .codeWord(createCardOrderRequest.getCodeWord())
                .createdAt(LocalDateTime.now())
                .period(createCardOrderRequest.getPeriod())
                .userId(1)
                .username("")
                .urgent(createCardOrderRequest.isUrgent())
                .status(OrderStatus.CREATED).build();

        log.debug("createCardOrder request: {}", ConvertUtil.convertObjectToJsonString(cardOrderDto));
        var cardOrder = cardOrderMapper.toEntity(cardOrderDto);
        cardOrder.setUserId(USER_ID1);
        cardOrder = cardOrderRepository.save(cardOrder);
        return cardOrderMapper.toDto(cardOrder);
    }

    public CardOrderDto editCardOrder(CardOrderDto cardOrderDto) {
        var cardOrder = cardOrderRepository.findById(cardOrderDto.getId())
                .orElseThrow(() -> new NotFoundException(ValidationMessage.CARD_ORDER_NOT_FOUND));
        log.debug("EditCardOrder request: {}", ConvertUtil.convertObjectToJsonString(cardOrderDto));

        checkStatus(cardOrder.getStatus());
        checkPeriod(cardOrderDto.getPeriod());

        //Status of the order can not be changed
        if (cardOrder.getStatus() != cardOrderDto.getStatus()) {
            throw InvalidInputException.of(ValidationMessage.CARD_ORDER_STATUS_CHANGE_ATTEMPT);
        }

        //Log the operation being carried out
        cardOrderOperationService.createOperation(cardOrder.getId(),
                CardOrderOperationType.EDITION,
                cardOrder.getStatus(),
                OrderStatus.EDITED,
                null);

        cardOrderDto.setStatus(OrderStatus.EDITED);
        cardOrder = cardOrderRepository.save(cardOrderMapper.toEntity(cardOrderDto));
        return cardOrderMapper.toDto(cardOrder);
    }

    public CardOrderDto deleteCardOrder(Long id) {
        var cardOrder = cardOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ValidationMessage.CARD_ORDER_NOT_FOUND));
        log.debug("The cardOrder {} has been deleted", id);
        //Submitted order can not be canged
        checkStatus(cardOrder.getStatus());

        //Log the operation being carried out
        cardOrderOperationService.createOperation(cardOrder.getId(),
                CardOrderOperationType.DELETION,
                cardOrder.getStatus(),
                OrderStatus.DELETED,
                null);

        //Delete from the main table
        cardOrder.setStatus(OrderStatus.DELETED);
        cardOrderRepository.save(cardOrder);
        return cardOrderMapper.toDto(cardOrder);
    }

    public CardOrderDto submitCardOrder(Long id) {
        var cardOrder = cardOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ValidationMessage.CARD_ORDER_NOT_FOUND));
        log.debug("The cardOrder {} has been submitted", id);

        checkStatus(cardOrder.getStatus());

        //Log the operation being carried out
        cardOrderOperationService.createOperation(cardOrder.getId(),
                CardOrderOperationType.SUBMISSION,
                cardOrder.getStatus(),
                OrderStatus.SUBMITTED,
                null);

        cardOrder.setStatus(OrderStatus.SUBMITTED);
        cardOrder = cardOrderRepository.save(cardOrder);

        var cardOrderEvent = CardOrderEvent.builder()
                .id(cardOrder.getId())
                .status(cardOrder.getStatus())
                .userId(cardOrder.getUserId())
                .username(cardOrder.getUsername())
                .cardType(cardOrder.getCardType())
                .cardHolderFullName(cardOrder.getCardHolderFullName())
                .cardHolderPin(cardOrder.getCardHolderPin())
                .period(cardOrder.getPeriod())
                .codeWord(cardOrder.getCodeWord())
                .urgent(cardOrder.isUrgent()).build();

        messageProducer.publish(RabbitMQConstants.EXCHANGE_TRANSFER,
                RabbitMQConstants.ROUTING_KEY_CARD_ORDER_SUBMISSION,
                cardOrderEvent);

        return cardOrderMapper.toDto(cardOrder);
    }

    private void checkStatus(OrderStatus orderStatus) {
        if (orderStatus == OrderStatus.SUBMITTED) {
            throw InvalidInputException.of(ValidationMessage.CARD_ORDER_SUBMITTED);
        }

        if (orderStatus == OrderStatus.COMPLETED) {
            throw InvalidInputException.of(ValidationMessage.CARD_ORDER_COMPLETED);
        }
    }

    private void checkPeriod(Integer period) {
        if(!Arrays.asList(12,24,36).contains(period)) {
            throw InvalidInputException.of(ValidationMessage.CARD_ORDER_INVALID_PERIOD);
        }
    }

}
