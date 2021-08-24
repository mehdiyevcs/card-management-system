package az.company.card.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Component
@Getter
@Setter
public class KafkaProperties {

    @Value(value = "${app.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${app.kafka.application-id-config}")
    private String applicationId;

    @Value("${app.kafka.transaction-id-prefix}")
    private String transactionIdPrefix;

    @Value("${app.kafka.idempotence}")
    private String idempotence;

    @Value("${app.kafka.acks}")
    private String acks;

    @Value("${app.kafka.topic.card-order-storage}")
    private String topicCardOrderStorge;

    @Value("${app.kafka.topic.card-order-processing}")
    private String topicCardOrderProcessing;
}
