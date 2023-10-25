package com.shoes.service;

import com.shoes.service.dto.DiscountShoesDetailsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.shoes.domain.DiscountShoesDetails}.
 */
public interface DiscountShoesDetailsService {
    /**
     * Save a discountShoesDetails.
     *
     * @param discountShoesDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    DiscountShoesDetailsDTO save(DiscountShoesDetailsDTO discountShoesDetailsDTO);

    /**
     * Updates a discountShoesDetails.
     *
     * @param discountShoesDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    DiscountShoesDetailsDTO update(DiscountShoesDetailsDTO discountShoesDetailsDTO);

    /**
     * Partially updates a discountShoesDetails.
     *
     * @param discountShoesDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DiscountShoesDetailsDTO> partialUpdate(DiscountShoesDetailsDTO discountShoesDetailsDTO);

    /**
     * Get all the discountShoesDetails.
     *
     * @return the list of entities.
     */
    List<DiscountShoesDetailsDTO> findAll();

    /**
     * Get the "id" discountShoesDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DiscountShoesDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" discountShoesDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
