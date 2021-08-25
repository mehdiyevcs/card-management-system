package az.company.cardorder.service;

import az.company.cardorder.domain.enumeration.CardOrderOperationType;
import az.company.cardorder.domain.enumeration.OrderStatus;
import az.company.cardorder.dto.CardOrderOperationDto;
import az.company.cardorder.mapper.CardOrderOperationMapper;
import az.company.cardorder.repository.CardOrderOperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CardOrderOperationService {

    private final CardOrderOperationRepository cardOrderOperationRepository;
    private final CardOrderOperationMapper cardOrderOperationMapper;

    public CardOrderOperationDto save(CardOrderOperationDto cardOrderOperationDto) {
        var cardOrderOperation = cardOrderOperationRepository
                .save(cardOrderOperationMapper.toEntity(cardOrderOperationDto));
        return cardOrderOperationMapper.toDto(cardOrderOperation);
    }

    public void createOperation(Long cardOrderId,
                                 CardOrderOperationType operationType,
                                 OrderStatus oldStatus,
                                 OrderStatus newStatus,
                                 String description) {
        log.info("Status of the card order {} changed from {} to {}",
                cardOrderId, oldStatus, newStatus);
        if (Objects.isNull(description))
            description = String.format("Status changed from %s to %s",
                    oldStatus, newStatus);

        var cardOrderOperationDto = CardOrderOperationDto.builder()
                .cardOrder(cardOrderId)
                .createdAt(LocalDateTime.now())
                .orderOperationType(operationType)
                .createdBy("Anon")
                .description(description)
                .build();
        save(cardOrderOperationDto);
    }
}
