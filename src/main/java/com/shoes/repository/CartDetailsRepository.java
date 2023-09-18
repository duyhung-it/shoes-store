package com.shoes.repository;

import com.shoes.domain.CartDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CartDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, Long> {}
