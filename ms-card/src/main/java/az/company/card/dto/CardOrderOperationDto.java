package az.company.card.dto;

import az.company.card.domain.enumeration.CardOrderOperationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardOrderOperationDto {
    private Long id;
    private CardOrderOperationType orderOperationType;
    private String description;
    private LocalDateTime createdAt;
    private String createdBy;
    private Long cardOrder;
}
