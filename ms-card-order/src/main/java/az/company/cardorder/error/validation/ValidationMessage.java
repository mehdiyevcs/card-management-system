package az.company.cardorder.error.validation;

/**
 * @author MehdiyevCS on 23.08.21
 */
public interface ValidationMessage {
    String PIN = "validation.invalidPin.message";

    String CARD_ORDER_NOT_FOUND = "cardOrder.notFound.message";
    String CARD_ORDER_SUBMITTED = "cardOrder.submitted.message";
}
