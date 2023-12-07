package com.shoes.repository;

import com.shoes.domain.OrderReturnDetails;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrderReturnDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderReturnDetailsRepository extends JpaRepository<OrderReturnDetails, Long> {
    List<OrderReturnDetails> findAllByOrderReturn_IdAndStatus(Long id, Integer status);
}
