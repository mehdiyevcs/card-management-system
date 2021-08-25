package az.company.card.dto;

import az.company.card.domain.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class CardDto {
    private Long id;
    private Long orderId;
    private Long customerId;
    private LocalDateTime createdAt;
    private CardType cardType;
    private String cardNumber;
    private String accountNumber;
}
