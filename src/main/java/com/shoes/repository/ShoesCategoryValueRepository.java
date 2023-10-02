package com.shoes.repository;

import com.shoes.domain.ShoesCategoryValue;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ShoesCategoryValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoesCategoryValueRepository extends JpaRepository<ShoesCategoryValue, Long> {
    List<ShoesCategoryValue> findAllByCategory_IdAndStatus(Long idShoesCategory, Integer status);
}
