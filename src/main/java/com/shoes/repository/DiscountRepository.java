package com.shoes.repository;

import com.shoes.domain.Discount;
import com.shoes.domain.Order;
import com.shoes.repository.custom.DiscountRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Discount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long>, DiscountRepositoryCustom {
    List<Discount> findAllByStatus(Integer status);
    Discount findByIdAndStatus(Long id, Integer status);

    @Query(value = "select d from Discount d where d.status <> -1 and (d.code like :searchText or d.name like :searchText)")
    List<Discount> searchByNameOrCode(@Param("searchText") String searchText);

    @Query(value = "SELECT jo.* FROM discount jo WHERE jo.created_date LIKE :date", nativeQuery = true)
    List<Discount> findByCreatedDate(@Param("date") String date);

    @Query(
        value = "SELECT o.* from discount o where o.start_date <= now() and o.end_date > now() and o.discount_status = 0 and o.status = 1",
        nativeQuery = true
    )
    List<Discount> findAllActive();

    @Query(value = "SELECT o.* from discount o where o.end_date <= now() and o.discount_status = 1 and o.status = 1", nativeQuery = true)
    List<Discount> findAllHetHan();
}
