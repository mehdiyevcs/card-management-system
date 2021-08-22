package az.company.card.mapper;

import az.company.card.domain.CardOrder;
import az.company.card.dto.CardOrderDto;
import org.mapstruct.Mapper;

/**
 * @author MehdiyevCS on 22.08.21
 */
@Mapper(componentModel = "spring", uses = {})
public interface CardOrderMapper extends EntityMapper<CardOrderDto, CardOrder>{
    default CardOrder fromId(Long id) {
        if (id == null)
            return null;

        CardOrder carOrder = new CardOrder();
        carOrder.setId(id);
        return carOrder;
    }
}
