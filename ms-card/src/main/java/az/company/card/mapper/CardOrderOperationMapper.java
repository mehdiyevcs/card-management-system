package az.company.card.mapper;

import az.company.card.domain.CardOrderOperation;
import az.company.card.dto.CardOrderOperationDto;
import org.mapstruct.Mapper;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Mapper(componentModel = "spring", uses = {})
public interface CardOrderOperationMapper extends EntityMapper<CardOrderOperationDto, CardOrderOperation> {
    default CardOrderOperation fromId(Long id) {
        if (id == null)
            return null;

        var cardOrderOperation = new CardOrderOperation();
        cardOrderOperation.setId(id);
        return cardOrderOperation;
    }
}
