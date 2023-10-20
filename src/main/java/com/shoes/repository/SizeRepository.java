package com.shoes.repository;

import com.shoes.domain.Size;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Size entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {}
