package az.company.card.service;

import az.company.card.dto.CardOrderOperationDto;
import az.company.card.mapper.CardOrderOperationMapper;
import az.company.card.repository.CardOrderOperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author MehdiyevCS on 23.08.21
 */
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
}
