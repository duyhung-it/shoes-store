package com.shoes.repository;

import com.shoes.domain.Cart;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select cart from Cart cart where cart.owner.login = ?#{principal.username}")
    List<Cart> findByOwnerIsCurrentUser();

    Cart findByOwnerId(Long id);
}
