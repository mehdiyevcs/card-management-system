package az.company.cardorder.model;

import az.company.cardorder.domain.enumeration.CardType;
import az.company.cardorder.error.validation.constraints.Pin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCardOrderRequest {
    @NotNull
    private CardType cardType;
    @NotNull
    private String cardHolderFullName;
    @NotNull
    private Integer period;
    private boolean urgent;
    @NotNull
    private String codeWord;
    @Pin
    private String cardHolderPin;
}
