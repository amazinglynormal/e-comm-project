package sb.ecomm.order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findOrderByStripePaymentIntentId(String stripePaymentIntentId);

    @Query(
            "SELECT o FROM Order o WHERE o.user.id = :userId AND o.status NOT IN (:statuses) ORDER BY o.datePlaced DESC"
    )
    List<Order> findUserOrders(@Param("userId") UUID userId, @Param("statuses") Collection<OrderStatus> statuses);
}
