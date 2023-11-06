package com.shoes.repository;

import com.shoes.domain.ShoesDetails;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data JPA repository for the ShoesDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShoesDetailsRepository extends JpaRepository<ShoesDetails, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE ShoesDetails sd SET sd.status = 0 WHERE sd.id = :shoesDetailsId")
    int softDeleteShoesDetailsById(@Param("shoesDetailsId") Long shoesDetailsId);

    List<ShoesDetails> findAllByShoes_IdInAndStatus(List<Long> ids, Integer status);

    @Query(
        value = "select sd.*\n" +
        "from shoes s join shoes_details sd on sd.shoes_id = s.id where s.id in :ids and sd.status = :status group by s.id,sd.color_id",
        nativeQuery = true
    )
    List<ShoesDetails> findShoesDetailsGroupByColor(@Param("ids") List<Long> ids, @Param("status") Integer status);
}
