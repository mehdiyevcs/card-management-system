package az.company.customer.model;

import az.company.customer.error.validation.constraints.Pin;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author MehdiyevCS on 24.08.21
 */
@Data
public class CreateCustomerRequest {
    @Pin
    private String pin;

    @NotNull
    private String fullName;
}
