package az.company.card.config;

import az.company.card.config.properties.KafkaProperties;
import az.company.card.domain.CardOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Configuration
@Slf4j
public class KafkaProducerConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<String, CardOrder> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();

        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaProperties.getBootstrapServers());

        configProps.put(
                ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,
                kafkaProperties.getIdempotence());

        configProps.put(
                ProducerConfig.TRANSACTIONAL_ID_CONFIG,
                kafkaProperties.getTransactionIdPrefix());

        configProps.put(
                ProducerConfig.ACKS_CONFIG,
                kafkaProperties.getAcks());

        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);

        DefaultKafkaProducerFactory factory = new DefaultKafkaProducerFactory<>(configProps);
        factory.transactionCapable();

        return factory;
    }

    @Bean
    public KafkaTemplate<String, CardOrder> orderKafkaTemplate() {
        var kafkaTemplate =
                new KafkaTemplate<>(producerFactory());

        kafkaTemplate.setProducerListener(new ProducerListener<>() {
            @Override
            public void onSuccess(
                    ProducerRecord<String, CardOrder> producerRecord,
                    RecordMetadata recordMetadata) {

                log.info("ACK from ProducerListener message(operationId): {}, uuid {} offset:  {}",
                        producerRecord.key(),
                        "",//producerRecord.value().getUuid(),
                        recordMetadata.offset());
            }

            @Override
            public void onError(ProducerRecord<String, CardOrder> producerRecord, Exception exception) {
                log.info("ACK from ProducerListener message(operationId): {}, uuid {} exception:  {}",
                        producerRecord.key(),
                        "",//producerRecord.value().getUuid(),
                        exception.getMessage());
                exception.printStackTrace();
                throw new RuntimeException("Unable to connect Kafka, try later again");
            }
        });
        return kafkaTemplate;
    }
}
