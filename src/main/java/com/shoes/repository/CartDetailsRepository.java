package com.shoes.repository;

import com.shoes.domain.Cart;
import com.shoes.domain.CartDetails;
import com.shoes.service.dto.CartDetailsDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for the CartDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetails, Long> {

    List<CartDetails> findCartDetailsByCart(Cart cart);

    Long countByCart(Cart cart);
}
