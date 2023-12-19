package com.shoes.repository;

import com.shoes.domain.Shoes;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;

import com.shoes.domain.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Shoes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoesRepository extends JpaRepository<Shoes, Long> {
    List<Shoes> findAllByStatus(Integer status);
    Shoes findByIdAndStatus(Long id, Integer status);
    Page<Shoes> findByStatus(int status, Pageable pageable);
}
