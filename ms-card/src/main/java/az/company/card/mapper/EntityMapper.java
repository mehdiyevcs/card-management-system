package az.company.card.mapper;

import java.util.List;

/**
 * @author MehdiyevCS on 22.08.21
 */

public interface EntityMapper<D, E> {

    E toEntity(D dto);

    List<E> toEntity(List<D> dtoList);

    D toDto(E entity);

    List<D> toDto(List<E> entityList);
}

