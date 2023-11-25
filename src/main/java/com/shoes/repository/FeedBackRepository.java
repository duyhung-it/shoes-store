package com.shoes.repository;

import com.shoes.domain.FeedBack;
import com.shoes.service.dto.ShoesFeedBackDTO;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FeedBack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
    @Query("select feedBack from FeedBack feedBack where feedBack.user.login = ?#{principal.username}")
    List<FeedBack> findByUserIsCurrentUser();

    @Query(
        value = "SELECT fb.*, u.first_name AS name " +
        "FROM feed_back fb " +
        "JOIN shoes_details sd ON fb.shoes_id = sd.id " +
        "JOIN jhi_user u ON fb.user_id = u.id " +
        "WHERE sd.shoes_id = :shid AND sd.brand_id = :brid and fb.status = 1",
        nativeQuery = true
    )
    List<ShoesFeedBackDTO> findAllFeedBackByShoesAndBrandDTO(@Param("shid") Integer shid, @Param("brid") Integer brid);
}
