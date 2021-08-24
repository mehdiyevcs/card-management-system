package az.company.card.mapper;

import az.company.card.domain.Card;
import az.company.card.dto.CardDto;
import org.mapstruct.Mapper;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Mapper(componentModel = "spring", uses = {})
public interface CardMapper extends EntityMapper<CardDto, Card> {
    default Card fromId(Long id) {
        if (id == null)
            return null;

        var card = new Card();
        card.setId(id);
        return card;
    }
}
