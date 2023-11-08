package com.shoes.repository;

import com.shoes.domain.OrderDetails;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrderDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    List<OrderDetails> findAllByOrder_IdAndStatus(Long id, Integer active);
    List<OrderDetails> findAllByOrder_IdInAndStatus(List<Long> orderId, Integer active);
}
