package az.company.cardorder.config;

import az.company.cardorder.constant.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public DirectExchange cmsExchange() {
        return new DirectExchange(RabbitMQConstants.EXCHANGE_TRANSFER);
    }

    @Bean
    public Queue cardOrderSubmissionResultQueue() {
        return new Queue(RabbitMQConstants.QUEUE_CARD_ORDER_SUBMISSION_RESULT);
    }

    @Bean
    public Binding cardOrderSubmissionResultBinding(Queue cardOrderSubmissionResultQueue,
                                                    DirectExchange cmsExchange) {
        return BindingBuilder.bind(cardOrderSubmissionResultQueue)
                .to(cmsExchange)
                .with(RabbitMQConstants.ROUTING_KEY_CARD_ORDER_SUBMISSION_RESULT);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
