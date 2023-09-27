package com.shoes.repository;

import com.shoes.domain.ShoesCategory;
import com.shoes.repository.custom.ShoesCategoryRepositoryCustom;
import java.time.Instant;
import java.util.Optional;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ShoesCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoesCategoryRepository extends JpaRepository<ShoesCategory, Long>, ShoesCategoryRepositoryCustom {
    Long countByCodeAndStatus(@Param("code") String code, @Param("status") Integer status);
    Optional<ShoesCategory> findByIdAndStatus(Long id, Integer status);

    @Query(
        value = "update ShoesCategory sc " +
        " set sc.status = :status," +
        " sc.lastModifiedBy = :lastModifiedBy," +
        " sc.lastModifiedDate = :lastModifiedDate " +
        " where sc.id = :id "
    )
    void updateStatus(
        @Param("id") Long id,
        @Param("status") Integer status,
        @Param("lastModifiedBy") String lastModifiedBy,
        @Param("lastModifiedDate") Instant lastModifiedDate
    );
}
