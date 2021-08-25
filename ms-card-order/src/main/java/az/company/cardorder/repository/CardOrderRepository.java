package az.company.cardorder.repository;

import az.company.cardorder.domain.CardOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author MehdiyevCS on 22.08.21
 */
@Repository
public interface CardOrderRepository extends JpaRepository<CardOrder, Long> {
    List<CardOrder> findAllByUsername(String username);

    Optional<CardOrder> findByIdAndUsername(Long id, String username);
}
