package com.shoes.repository;

import com.shoes.domain.OrderReturnDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrderReturnDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderReturnDetailsRepository extends JpaRepository<OrderReturnDetails, Long> {}
