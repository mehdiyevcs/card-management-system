package az.company.cardorder.service;

import az.company.cardorder.constant.RabbitMQConstants;
import az.company.cardorder.domain.CardOrder;
import az.company.cardorder.domain.enumeration.CardOrderOperationType;
import az.company.cardorder.domain.enumeration.OrderStatus;
import az.company.cardorder.error.exception.NotFoundException;
import az.company.cardorder.error.validation.ValidationMessage;
import az.company.cardorder.messaging.MessageProducer;
import az.company.cardorder.messaging.event.CardOrderEvent;
import az.company.cardorder.messaging.event.CardOrderResultEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Slf4j
@Service
@AllArgsConstructor
public class ProcessingService {

    private final MessageProducer messageProducer;
    private final CardOrderOperationService cardOrderOperationService;
    private final CardOrderService cardOrderService;

    public void publishCardOrderEvent(CardOrder cardOrder) {
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
    }

    public void processCardOrderResultEvent(CardOrderResultEvent cardOrderResultEvent) {
        var cardOrder = cardOrderService.getCardOrder(cardOrderResultEvent.getId())
                .orElseThrow(() -> new NotFoundException(ValidationMessage.CARD_ORDER_NOT_FOUND));

        CardOrderOperationType cardOrderOperationType =
                (cardOrderResultEvent.getOrderStatus() == OrderStatus.COMPLETED) ?
                        CardOrderOperationType.COMPLETED : CardOrderOperationType.FAILURE;

        //Log the operation being carried out
        cardOrderOperationService.createOperation(cardOrder.getId(),
                cardOrderOperationType,
                cardOrder.getStatus(),
                cardOrderResultEvent.getOrderStatus(),
                cardOrderResultEvent.getDescription());

        cardOrder.setStatus(cardOrderResultEvent.getOrderStatus());
        cardOrderService.save(cardOrder);
    }
}
