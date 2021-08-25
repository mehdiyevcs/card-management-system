package az.company.customer.error.validation;

/**
 * @author MehdiyevCS on 23.08.21
 */
public interface ValidationMessage {
    String PIN = "validation.invalidPin.message";

    String CUSTOMER_NOT_FOUND = "customer.notFound.message";
    String CUSTOMER_DUPLICATE = "customer.duplicate.message";
    String CUSTOMER_MISSING_PARAMETER = "customer.missingParameter.message";
}
