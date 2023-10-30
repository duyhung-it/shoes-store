package com.shoes.repository;

import com.shoes.config.Constants;
import com.shoes.domain.DiscountShoesDetails;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DiscountShoesDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountShoesDetailsRepository extends JpaRepository<DiscountShoesDetails, Long> {
    List<DiscountShoesDetails> findAllByDiscount_IdAndStatus(Long idDiscount, Integer status);
}
