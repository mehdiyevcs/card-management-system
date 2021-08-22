package az.company.card.repository;

import az.company.card.domain.CardOrder;
import az.company.card.domain.enumeration.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author MehdiyevCS on 22.08.21
 */
@Repository
public interface CardOrderRepository extends JpaRepository<CardOrder, Long> {

    List<CardOrder> findAllByUserId(Long userId);

    Optional<CardOrder> findByIdAndUserId(Long id, Long UserId);

    List<CardOrder> findByStatusAndUserId(OrderStatus orderStatus, Long userId);
}
