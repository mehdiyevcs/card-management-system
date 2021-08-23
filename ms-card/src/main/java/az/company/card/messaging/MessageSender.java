package az.company.card.messaging;

import az.company.card.config.properties.KafkaProperties;
import az.company.card.domain.CardOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author MehdiyevCS on 23.08.21
 */
@RequiredArgsConstructor
@Component
public class MessageSender {

    private final KafkaTemplate<String, CardOrder> kafkaTemplate;
    private final KafkaProperties kafkaProperties;

    @Transactional
    public void send(CardOrder entity) {

        //should be in single transaction
        try {
            kafkaTemplate.send(kafkaProperties.getTopicCardOrderProcessing(),
                    entity.getId().toString(), entity);

            kafkaTemplate.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
