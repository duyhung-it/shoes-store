package com.shoes.repository;

import com.shoes.domain.Discount;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Discount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
    List<Discount> findAllByStatus(Integer status);
    Discount findByIdAndStatus(Long id, Integer status);

    @Query(value = "select d from Discount d where d.status <> -1 and (d.code like :searchText or d.name like :searchText)")
    List<Discount> searchByNameOrCode(@Param("searchText") String searchText);
}
