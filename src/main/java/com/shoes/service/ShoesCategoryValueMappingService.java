package com.shoes.service;

import com.shoes.service.dto.ShoesCategoryValueMappingDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.shoes.domain.ShoesCategoryValueMapping}.
 */
public interface ShoesCategoryValueMappingService {
    /**
     * Save a shoesCategoryValueMapping.
     *
     * @param shoesCategoryValueMappingDTO the entity to save.
     * @return the persisted entity.
     */
    ShoesCategoryValueMappingDTO save(ShoesCategoryValueMappingDTO shoesCategoryValueMappingDTO);

    /**
     * Updates a shoesCategoryValueMapping.
     *
     * @param shoesCategoryValueMappingDTO the entity to update.
     * @return the persisted entity.
     */
    ShoesCategoryValueMappingDTO update(ShoesCategoryValueMappingDTO shoesCategoryValueMappingDTO);

    /**
     * Partially updates a shoesCategoryValueMapping.
     *
     * @param shoesCategoryValueMappingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShoesCategoryValueMappingDTO> partialUpdate(ShoesCategoryValueMappingDTO shoesCategoryValueMappingDTO);

    /**
     * Get all the shoesCategoryValueMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShoesCategoryValueMappingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shoesCategoryValueMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShoesCategoryValueMappingDTO> findOne(Long id);

    /**
     * Delete the "id" shoesCategoryValueMapping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
