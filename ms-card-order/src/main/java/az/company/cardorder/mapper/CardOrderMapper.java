package az.company.cardorder.mapper;

import az.company.cardorder.domain.CardOrder;
import az.company.cardorder.dto.CardOrderDto;
import org.mapstruct.Mapper;

/**
 * @author MehdiyevCS on 22.08.21
 */
@Mapper(componentModel = "spring", uses = {})
public interface CardOrderMapper extends EntityMapper<CardOrderDto, CardOrder> {
    default CardOrder fromId(Long id) {
        if (id == null)
            return null;

        var carOrder = new CardOrder();
        carOrder.setId(id);
        return carOrder;
    }
}
