package com.shoes.repository;

import com.shoes.domain.ShoesCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ShoesCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoesCategoryRepository extends JpaRepository<ShoesCategory, Long> {}
