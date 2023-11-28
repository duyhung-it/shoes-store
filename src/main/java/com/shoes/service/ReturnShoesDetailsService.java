package com.shoes.service;

import com.shoes.service.dto.ReturnShoesDetailsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.shoes.domain.ReturnShoesDetails}.
 */
public interface ReturnShoesDetailsService {
    /**
     * Save a returnShoesDetails.
     *
     * @param returnShoesDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    ReturnShoesDetailsDTO save(ReturnShoesDetailsDTO returnShoesDetailsDTO);

    /**
     * Updates a returnShoesDetails.
     *
     * @param returnShoesDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    ReturnShoesDetailsDTO update(ReturnShoesDetailsDTO returnShoesDetailsDTO);

    /**
     * Partially updates a returnShoesDetails.
     *
     * @param returnShoesDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReturnShoesDetailsDTO> partialUpdate(ReturnShoesDetailsDTO returnShoesDetailsDTO);

    /**
     * Get all the returnShoesDetails.
     *
     * @return the list of entities.
     */
    List<ReturnShoesDetailsDTO> findAll();

    /**
     * Get the "id" returnShoesDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReturnShoesDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" returnShoesDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
