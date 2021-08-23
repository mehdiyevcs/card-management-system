package az.company.card.config;

import az.company.card.config.properties.KafkaProperties;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Configuration
public class KafkaTopicConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootstrapServers());
        configs.put(AdminClientConfig.CLIENT_ID_CONFIG, kafkaProperties.getApplicationId());
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicCardOrderProcessing() {
        return TopicBuilder
                .name(kafkaProperties.getTopicCardOrderProcessing())
                .partitions(1)
                .build();
    }
}
