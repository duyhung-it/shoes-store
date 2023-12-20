package com.shoes.repository;

import com.shoes.domain.FeedBack;
import com.shoes.service.dto.ShoesFeedBackDTO;
import java.util.List;
import javax.transaction.Transactional;
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

    @Transactional
    @Modifying
    @Query("UPDATE FeedBack f SET f.status = :status WHERE f.id = :id")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);

    @Query(
        value = "SELECT EXISTS (\n" +
        "    SELECT 1 \n" +
        "    FROM feed_back" +
        "    WHERE user_id = :uid AND shoes_id  in ( select id from shoes_details where brand_id = :brid and shoes_id = :shid  ) " +
        ");",
        nativeQuery = true
    )
    Integer checkComment(@Param("uid") Long id, @Param("brid") Long brid, @Param("shid") Long shid);

    @Query(
        value = "SELECT EXISTS (\n" +
        "    SELECT 1 \n" +
        "    FROM jhi_order\n" +
        "    JOIN order_details od ON jhi_order.id = od.order_id \n" +
        "    WHERE jhi_order.owner_id = :uid AND od.shoes_details_id  in ( select id from shoes_details where brand_id = :brid and shoes_id = :shid ) and jhi_order.status = 3 " +
        ");",
        nativeQuery = true
    )
    Integer checkBuy(@Param("uid") Long id, @Param("brid") Long brid, @Param("shid") Long shid);

    Integer countAllByStatus(Integer status);
}
