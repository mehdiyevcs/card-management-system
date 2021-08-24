package az.company.cardorder.service;

import az.company.cardorder.dto.CardOrderOperationDto;
import az.company.cardorder.mapper.CardOrderOperationMapper;
import az.company.cardorder.repository.CardOrderOperationRepository;
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
