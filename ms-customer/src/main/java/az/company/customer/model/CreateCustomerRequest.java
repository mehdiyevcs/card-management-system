package az.company.customer.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author MehdiyevCS on 24.08.21
 */
@Data
public class CreateCustomerRequest {
    @NotNull
    private String pin;

    @NotNull
    private String fullName;
}
