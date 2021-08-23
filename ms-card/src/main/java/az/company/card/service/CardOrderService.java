package az.company.card.service;

import az.company.card.domain.enumeration.CardOrderOperationType;
import az.company.card.domain.enumeration.OrderStatus;
import az.company.card.dto.CardOrderDto;
import az.company.card.dto.CardOrderOperationDto;
import az.company.card.error.exception.InvalidInputException;
import az.company.card.error.exception.NotFoundException;
import az.company.card.error.validation.ValidationMessage;
import az.company.card.mapper.CardOrderMapper;
import az.company.card.repository.CardOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author MehdiyevCS on 22.08.21
 */
@Service
@Transactional
@RequiredArgsConstructor
public class CardOrderService {

    //UserId will be extracted from ContextHolder
    private static final Long USER_ID1 = 12345L;

    private final CardOrderRepository cardOrderRepository;
    private final CardOrderMapper cardOrderMapper;
    private final CardOrderOperationService cardOrderOperationService;

    public List<CardOrderDto> getCardOrders() {
        return cardOrderRepository.findAllByUserId(USER_ID1)
                .stream()
                .map(cardOrderMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<CardOrderDto> getCardOrder(Long id) {
        return cardOrderRepository.findByIdAndUserId(id, USER_ID1).map(cardOrderMapper::toDto);
    }

    public CardOrderDto createCardOrder(CardOrderDto cardOrderDto) {
        var cardOrder = cardOrderMapper.toEntity(cardOrderDto);
        cardOrder.setUserId(USER_ID1);
        cardOrder = cardOrderRepository.save(cardOrder);
        return cardOrderMapper.toDto(cardOrder);
    }

    public CardOrderDto editCardOrder(CardOrderDto cardOrderDto) {
        var cardOrder = cardOrderRepository.findById(cardOrderDto.getId())
                .orElseThrow(() -> new NotFoundException(ValidationMessage.CARD_ORDER_NOT_FOUND));

        //Submitted order can not be canged
        if (cardOrder.getStatus() == OrderStatus.SUBMITTED) {
            throw InvalidInputException.of(ValidationMessage.CARD_ORDER_SUBMITTED);
        }

        //Log the operation being carried out
        var cardOrderOperationDto = createOperation(cardOrder.getId(),
                CardOrderOperationType.EDITION,
                cardOrder.getStatus(),
                OrderStatus.EDITED);
        cardOrderOperationService.save(cardOrderOperationDto);

        cardOrderDto.setStatus(OrderStatus.EDITED);
        cardOrder = cardOrderRepository.save(cardOrderMapper.toEntity(cardOrderDto));
        return cardOrderMapper.toDto(cardOrder);
    }

    public CardOrderDto deleteCardOrder(Long id) {
        var cardOrder = cardOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ValidationMessage.CARD_ORDER_NOT_FOUND));

        //Submitted order can not be canged
        if (cardOrder.getStatus() == OrderStatus.SUBMITTED) {
            throw InvalidInputException.of(ValidationMessage.CARD_ORDER_SUBMITTED);
        }
        //Log the operation being carried out
        var cardOrderOperationDto = createOperation(cardOrder.getId(),
                CardOrderOperationType.DELETION,
                cardOrder.getStatus(),
                OrderStatus.DELETED);
        cardOrderOperationService.save(cardOrderOperationDto);

        //Delete from the main table
        cardOrder.setStatus(OrderStatus.DELETED);
        cardOrderRepository.save(cardOrder);
        return cardOrderMapper.toDto(cardOrder);
    }

    public CardOrderDto submitCardOrder(Long id) {
        var cardOrder = cardOrderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ValidationMessage.CARD_ORDER_NOT_FOUND));

        //Log the operation being carried out
        if (cardOrder.getStatus() == OrderStatus.SUBMITTED) {
            throw InvalidInputException.of(ValidationMessage.CARD_ORDER_SUBMITTED);
        }

        //Log the operation being carried out
        var cardOrderOperationDto = createOperation(cardOrder.getId(),
                CardOrderOperationType.SUBMISSION,
                cardOrder.getStatus(),
                OrderStatus.SUBMITTED);
        cardOrderOperationService.save(cardOrderOperationDto);

        cardOrder.setStatus(OrderStatus.SUBMITTED);
        cardOrder = cardOrderRepository.save(cardOrder);

        //Kafka Event publishing, need to be implemented

        return cardOrderMapper.toDto(cardOrder);
    }

    private CardOrderOperationDto createOperation(Long cardOrderId,
                                                  CardOrderOperationType operationType,
                                                  OrderStatus oldStatus,
                                                  OrderStatus newStatus) {
        return CardOrderOperationDto.builder()
                .cardOrder(cardOrderId)
                .createdAt(LocalDateTime.now())
                .orderOperationType(operationType)
                .createdBy("Anon")
                .description(String.format("Status changed from %s to %s",
                        oldStatus, newStatus))
                .build();
    }

}
