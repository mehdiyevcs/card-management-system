package az.company.cardorder.messaging;

import az.company.cardorder.constant.RabbitMQConstants;
import az.company.cardorder.messaging.event.CardOrderResultEvent;
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

    @RabbitListener(queues = RabbitMQConstants.QUEUE_CARD_ORDER_SUBMISSION_RESULT)
    public void receiveCreateOrderSubmissionResult(CardOrderResultEvent cardOrderResultEvent) {
        log.info("Received CardOrderResultEvent: " + cardOrderResultEvent);
    }

}
