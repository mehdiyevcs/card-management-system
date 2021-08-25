package az.company.card.service;

import az.company.card.constant.RabbitMQConstants;
import az.company.card.dto.CardDto;
import az.company.card.messaging.MessageProducer;
import az.company.card.messaging.event.CardOrderEvent;
import az.company.card.messaging.event.CardOrderResultEvent;
import az.company.card.messaging.event.OrderStatus;
import az.company.card.util.CardNumberGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Service
@AllArgsConstructor
public class ProcessingService {

    private final MessageProducer messageProducer;
    private final CardService cardService;

    public void processCardOrderEvent(CardOrderEvent cardOrderEvent) {
        CardDto cardDto = CardDto.builder()
                .orderId(cardOrderEvent.getId())
                .customerId(12345L)
                .createdAt(LocalDateTime.now())
                .cardType(cardOrderEvent.getCardType())
                .cardNumber(CardNumberGenerator.generateMasterCardNumber())
                .accountNumber(CardNumberGenerator.generateMasterCardNumber()).build();
        cardService.save(cardDto);

        CardOrderResultEvent cardOrderResultEvent = CardOrderResultEvent.builder()
                .id(cardOrderEvent.getId())
                .orderStatus(OrderStatus.COMPLETED)
                .description("Success").build();
        messageProducer.publish(RabbitMQConstants.EXCHANGE_TRANSFER,
                RabbitMQConstants.ROUTING_KEY_CARD_ORDER_SUBMISSION_RESULT,
                cardOrderResultEvent);
    }

}
