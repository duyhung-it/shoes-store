package com.shoes.service;

import com.shoes.service.dto.ReturnOrderDetailsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.shoes.domain.ReturnOrderDetails}.
 */
public interface ReturnOrderDetailsService {
    /**
     * Save a returnOrderDetails.
     *
     * @param returnOrderDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    ReturnOrderDetailsDTO save(ReturnOrderDetailsDTO returnOrderDetailsDTO);

    /**
     * Updates a returnOrderDetails.
     *
     * @param returnOrderDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    ReturnOrderDetailsDTO update(ReturnOrderDetailsDTO returnOrderDetailsDTO);

    /**
     * Partially updates a returnOrderDetails.
     *
     * @param returnOrderDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReturnOrderDetailsDTO> partialUpdate(ReturnOrderDetailsDTO returnOrderDetailsDTO);

    /**
     * Get all the returnOrderDetails.
     *
     * @return the list of entities.
     */
    List<ReturnOrderDetailsDTO> findAll();

    /**
     * Get the "id" returnOrderDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReturnOrderDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" returnOrderDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
