package az.company.cardorder.service;

import az.company.cardorder.constant.RabbitMQConstants;
import az.company.cardorder.domain.CardOrder;
import az.company.cardorder.messaging.MessageProducer;
import az.company.cardorder.messaging.event.CardOrderEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Service
@AllArgsConstructor
public class ProcessingService {

    private final MessageProducer messageProducer;

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

}
