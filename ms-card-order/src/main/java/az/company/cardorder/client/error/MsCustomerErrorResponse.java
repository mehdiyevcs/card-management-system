package az.company.cardorder.client.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MsCustomerErrorResponse {
    private String id;
    private Integer code;
    private String message;
}
