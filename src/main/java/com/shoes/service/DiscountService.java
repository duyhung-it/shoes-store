package com.shoes.service;

import com.shoes.service.dto.DiscountCreateDTO;
import com.shoes.service.dto.DiscountDTO;
import com.shoes.service.dto.DiscountResDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.shoes.domain.Discount}.
 */
public interface DiscountService {
    /**
     * Save a discount.
     *
     * @param discountDTO the entity to save.
     * @return the persisted entity.
     */
    DiscountDTO save(DiscountCreateDTO discountDTO);

    /**
     * Updates a discount.
     *
     * @param discountDTO the entity to update.
     * @param id
     * @return the persisted entity.
     */
    DiscountDTO update(DiscountCreateDTO discountDTO, Long id);

    /**
     * Partially updates a discount.
     *
     * @param discountDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DiscountDTO> partialUpdate(DiscountDTO discountDTO);

    /**
     * Get all the discounts.
     *
     * @return the list of entities.
     */
    List<DiscountDTO> findAll();

    /**
     * Get the "id" discount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    DiscountResDTO findOne(Long id);

    /**
     * Delete the "id" discount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<DiscountDTO> search(String searchText);
}
