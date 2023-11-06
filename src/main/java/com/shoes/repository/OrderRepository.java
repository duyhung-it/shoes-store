package com.shoes.repository;

import com.shoes.domain.Order;
import com.shoes.repository.custom.OrderRepositoryCustom;
import com.shoes.service.dto.OrderDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    @Query("select jhiOrder from Order jhiOrder where jhiOrder.owner.login = ?#{principal.username}")
    List<Order> findByOwnerIsCurrentUser();

    Page<Order> getOrderByOwnerId(Long id, Pageable pageable);

    Optional<Order> findByIdAndStatus(Long id, Integer status);

    @Query(value = "SELECT * FROM jhi_order jo WHERE jo.created_date LIKE :date", nativeQuery = true)
    List<Order> findByCreatedDate(@Param("date") String date);
}
