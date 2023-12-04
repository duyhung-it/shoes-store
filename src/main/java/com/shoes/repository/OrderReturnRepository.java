package com.shoes.repository;

import com.shoes.domain.OrderReturn;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrderReturn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderReturnRepository extends JpaRepository<OrderReturn, Long> {
    @Query(value = "select * from order_return where created_date like :createdDate", nativeQuery = true)
    List<OrderReturn> findByCreatedDateLike(@Param("createdDate") String createdDate);
}
