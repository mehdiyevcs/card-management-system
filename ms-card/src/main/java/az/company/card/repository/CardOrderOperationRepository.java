package az.company.card.repository;

import az.company.card.domain.CardOrderOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author MehdiyevCS on 23.08.21
 */
@Repository
public interface CardOrderOperationRepository extends JpaRepository<CardOrderOperation, Long> {
}
