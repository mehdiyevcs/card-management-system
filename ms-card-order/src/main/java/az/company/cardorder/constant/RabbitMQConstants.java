package az.company.cardorder.constant;

/**
 * @author MehdiyevCS on 25.08.21
 */
public interface RabbitMQConstants {

    String  EXCHANGE_TRANSFER = "cms";

    String ROUTING_KEY_CARD_ORDER_SUBMISSION = "cms.cardOrder.submission";
    String ROUTING_KEY_CARD_ORDER_SUBMISSION_RESULT = "cms.cardOrder.submission.result";

    String QUEUE_CARD_ORDER_SUBMISSION_RESULT = "cms.cardOrder.submission.result.queue";
}
