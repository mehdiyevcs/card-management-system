package az.company.cardorder.mapper;

import az.company.cardorder.domain.CardOrderOperation;
import az.company.cardorder.dto.CardOrderOperationDto;
import az.company.cardorder.mapper.EntityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Mapper(componentModel = "spring", uses = {})
public interface CardOrderOperationMapper extends EntityMapper<CardOrderOperationDto, CardOrderOperation> {

    @Mapping(source = "cardOrder.id", target = "cardOrder")
    CardOrderOperationDto toDto(CardOrderOperation cardOrderOperation);

    @Mapping(source = "cardOrder", target = "cardOrder.id")
    CardOrderOperation toEntity(CardOrderOperationDto cardOrderOperationDto);

    default CardOrderOperation fromId(Long id) {
        if (id == null)
            return null;

        var cardOrderOperation = new CardOrderOperation();
        cardOrderOperation.setId(id);
        return cardOrderOperation;
    }
}
