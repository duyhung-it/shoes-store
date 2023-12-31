package com.shoes.repository;

import com.shoes.domain.Brand;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Brand entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Page<Brand> findByNameContainingAndCodeContaining(String name, String code, Pageable pageable);
    Page<Brand> findByNameContaining(String name, Pageable pageable);
    Page<Brand> findByCodeContaining(String code, Pageable pageable);
    Page<Brand> findByStatus(int status, Pageable pageable);
    List<Brand> findByIdInAndStatus(List<Long> ids, int status);
    Brand findByIdAndStatus(Long id, int status);
}
