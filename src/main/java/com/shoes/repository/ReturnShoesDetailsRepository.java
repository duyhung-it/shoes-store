package com.shoes.repository;

import com.shoes.domain.ReturnShoesDetails;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReturnShoesDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReturnShoesDetailsRepository extends JpaRepository<ReturnShoesDetails, Long> {
    List<ReturnShoesDetails> findAllByOrderReturnDetails_IdAndStatus(Long id, Integer status);
}
