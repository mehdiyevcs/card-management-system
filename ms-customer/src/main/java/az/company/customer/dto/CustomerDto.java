package az.company.customer.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author MehdiyevCS on 24.08.21
 */
@Data
public class CustomerDto {
    private Long id;
    @NotNull
    private String pin;
    @NotNull
    private String fullName;
}
