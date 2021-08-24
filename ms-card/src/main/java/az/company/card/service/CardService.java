package az.company.card.service;

import az.company.card.dto.CardDto;
import az.company.card.mapper.CardMapper;
import az.company.card.repository.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Service
@AllArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public CardDto save(CardDto cardDto) {
        var card = cardMapper.toEntity(cardDto);
        card = cardRepository.save(card);
        return cardMapper.toDto(card);
    }
}
