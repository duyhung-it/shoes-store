package com.shoes.repository;

import com.shoes.domain.Order;
import com.shoes.repository.custom.OrderRepositoryCustom;
import com.shoes.service.dto.OrderDTO;
import com.shoes.service.dto.RevenueDTO;
import java.math.BigDecimal;
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
    List<Order> findAllByIdInAndStatus(List<Long> orderIds, Integer status);

    @Query(value = "SELECT * FROM jhi_order jo WHERE jo.created_date LIKE :date", nativeQuery = true)
    List<Order> findByCreatedDate(@Param("date") String date);

    @Query(value = "select count(*) from jhi_order jo where week(now()) = week(jo.created_date) ", nativeQuery = true)
    Integer getOrderNumberInWeek();

    @Query(
        value = "select sum(jo.total_price) revenue  from jhi_order jo \n" + "where week(now()) = week(jo.created_date) and jo.status = 3",
        nativeQuery = true
    )
    BigDecimal getRevenueInWeek();

    @Query(
        value = "select count(*) from jhi_user ju \n" +
        "join jhi_user_authority jua on ju.id = jua.user_id \n" +
        "and jua.authority_name = 'ROLE_USER'\n" +
        "where ju.activated",
        nativeQuery = true
    )
    Integer getTotalCustomer();

    @Query(
        value = "select sum(jo.total_price) revenue  from jhi_order jo \n" +
        "where week(now()) = week(jo.created_date) and jo.status = 3 and jo.paid_method = 1",
        nativeQuery = true
    )
    BigDecimal getRevenueOnShop();

    @Query(
        value = "select coalesce(sum(jo.total_price),0)  revenue  from jhi_order jo \n" +
        "where week(now()) = week(jo.created_date) and jo.status = 3 and jo.paid_method = 2",
        nativeQuery = true
    )
    BigDecimal getRevenueOnline();

    List<Order> getOrderByStatusAndOwnerLogin(Integer status, String login);
}
