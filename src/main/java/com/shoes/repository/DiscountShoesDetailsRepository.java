package com.shoes.repository;

import com.shoes.config.Constants;
import com.shoes.domain.DiscountShoesDetails;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DiscountShoesDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountShoesDetailsRepository extends JpaRepository<DiscountShoesDetails, Long> {
    List<DiscountShoesDetails> findAllByDiscount_IdAndStatus(Long idDiscount, Integer status);

    @Query(
        value = "select * from discount_shoes_details dsd \n" +
        "join discount d on d.id = dsd.discount_id \n" +
        "and d.start_date <= now() and d.end_date >= now() \n" +
        "and d.status  = 1\n" +
        "where dsd.shoes_details_id = :shoesId and dsd.brand_id = :brandId and dsd.status = 1",
        nativeQuery = true
    )
    DiscountShoesDetails findByShoesIdAndStatus(@Param("shoesId") Long idShoes, @Param("brandId") Long idBrand);
}
