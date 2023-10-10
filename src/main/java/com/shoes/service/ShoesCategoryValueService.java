package com.shoes.service;

import com.shoes.service.dto.ShoesCategoryValueDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.shoes.domain.ShoesCategoryValue}.
 */
public interface ShoesCategoryValueService {
    /**
     * Save a shoesCategoryValue.
     *
     * @param shoesCategoryValueDTO the entity to save.
     * @return the persisted entity.
     */
    ShoesCategoryValueDTO save(ShoesCategoryValueDTO shoesCategoryValueDTO);

    /**
     * Updates a shoesCategoryValue.
     *
     * @param shoesCategoryValueDTO the entity to update.
     * @return the persisted entity.
     */
    ShoesCategoryValueDTO update(ShoesCategoryValueDTO shoesCategoryValueDTO);

    /**
     * Partially updates a shoesCategoryValue.
     *
     * @param shoesCategoryValueDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShoesCategoryValueDTO> partialUpdate(ShoesCategoryValueDTO shoesCategoryValueDTO);

    /**
     * Get all the shoesCategoryValues.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShoesCategoryValueDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shoesCategoryValue.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShoesCategoryValueDTO> findOne(Long id);

    /**
     * Delete the "id" shoesCategoryValue.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<ShoesCategoryValueDTO> findAllByShoesCategory(Long idShoesCategory);
}
