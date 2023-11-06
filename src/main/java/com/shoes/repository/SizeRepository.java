package com.shoes.repository;

import com.shoes.domain.Size;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Size entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    @Query(
        value = "select distinct  s.* from `size` s \n" +
        "join shoes_details sd on s.id  = sd.size_id \n" +
        "where sd.shoes_id = :shoesId and sd.color_id = :colorId and s.status = :status",
        nativeQuery = true
    )
    List<Size> findByShoesIdAndColor(@Param("shoesId") Long shoesId, @Param("colorId") Long colorId, @Param("status") Integer status);
}
