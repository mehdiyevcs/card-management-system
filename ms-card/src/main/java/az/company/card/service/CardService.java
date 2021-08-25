package az.company.card.service;

import az.company.card.dto.CardDto;
import az.company.card.error.exception.InvalidInputException;
import az.company.card.error.validation.ValidationMessage;
import az.company.card.mapper.CardMapper;
import az.company.card.repository.CardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Service
@AllArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public Optional<CardDto> getCard(Long id, Long orderId) {
        if(Objects.nonNull(id))
            return cardRepository.findById(id).map(cardMapper::toDto);

        if(Objects.nonNull(orderId))
            return cardRepository.findByOrderId(orderId).map(cardMapper::toDto);

        throw InvalidInputException.of(ValidationMessage.CARD_PARAMETER_MISSING);
    }

    public CardDto save(CardDto cardDto) {
        var card = cardMapper.toEntity(cardDto);
        card = cardRepository.save(card);
        return cardMapper.toDto(card);
    }
}
