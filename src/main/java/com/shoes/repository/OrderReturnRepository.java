package com.shoes.repository;

import com.shoes.domain.OrderReturn;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrderReturn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderReturnRepository extends JpaRepository<OrderReturn, Long> {}
