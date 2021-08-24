package az.company.cardorder.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageProducer {

    private final RabbitTemplate rabbitTemplate;

    public <T extends Serializable> void publish(String exchange, String routingKey, T event) {
        log.info("Published event: " + event.toString());
        rabbitTemplate.convertAndSend(exchange, routingKey, event);
    }

}
