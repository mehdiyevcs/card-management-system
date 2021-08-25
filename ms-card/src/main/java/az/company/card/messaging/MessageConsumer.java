package az.company.card.messaging;

import az.company.card.constant.RabbitMQConstants;
import az.company.card.messaging.event.CardOrderEvent;
import az.company.card.service.ProcessingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class MessageConsumer {

    private final ProcessingService processingService;

    @RabbitListener(queues = RabbitMQConstants.QUEUE_CARD_ORDER_SUBMISSION)
    public void receiveCreateOrderSubmission(CardOrderEvent cardOrderEvent) {
        log.info("Received CardOrderEvent: " + cardOrderEvent.toString());
        processingService.processCardOrderEvent(cardOrderEvent);
    }
}
