package az.company.cardorder.dto;

import az.company.cardorder.domain.enumeration.CardType;
import az.company.cardorder.domain.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author MehdiyevCS on 22.08.21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardOrderDto {
    private Long id;
    private OrderStatus status;
    private CardType cardType;
    private String cardHolderFullName;
    private Integer period;
    private boolean urgent;
    private String codeWord;
    //@Pin
    private String cardHolderPin;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer userId;
    private String username;
}
