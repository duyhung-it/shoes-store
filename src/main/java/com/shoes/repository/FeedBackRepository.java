package com.shoes.repository;

import com.shoes.domain.FeedBack;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FeedBack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Long> {
    @Query("select feedBack from FeedBack feedBack where feedBack.user.login = ?#{principal.username}")
    List<FeedBack> findByUserIsCurrentUser();
}
