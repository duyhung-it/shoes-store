package com.shoes.repository;

import com.shoes.domain.ReturnOrderDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ReturnOrderDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ReturnOrderDetailsRepository extends JpaRepository<ReturnOrderDetails, Long> {}
