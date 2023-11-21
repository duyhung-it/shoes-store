package com.shoes.service;

import com.shoes.domain.Cart;
import com.shoes.domain.CartDetails;
import com.shoes.service.dto.CartDetailsDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.shoes.domain.CartDetails}.
 */
public interface CartDetailsService {
    /**
     * Save a cartDetails.
     *
     * @param cartDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    CartDetailsDTO save(CartDetailsDTO cartDetailsDTO);

    /**
     * Updates a cartDetails.
     *
     * @param cartDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    CartDetailsDTO update(CartDetailsDTO cartDetailsDTO);

    /**
     * Partially updates a cartDetails.
     *
     * @param cartDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CartDetailsDTO> partialUpdate(CartDetailsDTO cartDetailsDTO);

    /**
     * Get all the cartDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CartDetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" cartDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CartDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" cartDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<CartDetailsDTO> findCartDetailsByCart(Cart cart);

    Long countByCart(Cart cart);
}
