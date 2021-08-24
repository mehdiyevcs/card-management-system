package az.company.customer.dto;

import com.sun.istack.NotNull;
import lombok.Data;

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
