package com.shoes.repository;

import com.shoes.domain.ShoesCategoryValueMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ShoesCategoryValueMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoesCategoryValueMappingRepository extends JpaRepository<ShoesCategoryValueMapping, Long> {}
