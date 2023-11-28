package com.shoes.repository;

import com.shoes.domain.ReturnShoesDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReturnShoesDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReturnShoesDetailsRepository extends JpaRepository<ReturnShoesDetails, Long> {}
