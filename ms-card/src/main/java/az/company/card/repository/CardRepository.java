package az.company.card.repository;

import az.company.card.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author MehdiyevCS on 25.08.21
 */
@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByOrderId(Long orderId);
}
