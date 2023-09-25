package com.shoes.repository;

import com.shoes.domain.ShoesFileUploadMapping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ShoesFileUploadMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoesFileUploadMappingRepository extends JpaRepository<ShoesFileUploadMapping, Long> {}