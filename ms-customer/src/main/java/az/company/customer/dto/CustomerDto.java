package az.company.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author MehdiyevCS on 24.08.21
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CustomerDto {
    private Long id;
    @NotNull
    private String pin;
    @NotNull
    private String fullName;
    private Long userId;
}
