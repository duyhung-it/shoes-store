package com.shoes.service;

import com.shoes.service.dto.OrderReturnDetailsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.shoes.domain.OrderReturnDetails}.
 */
public interface OrderReturnDetailsService {
    /**
     * Save a orderReturnDetails.
     *
     * @param orderReturnDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    OrderReturnDetailsDTO save(OrderReturnDetailsDTO orderReturnDetailsDTO);

    /**
     * Updates a orderReturnDetails.
     *
     * @param orderReturnDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    OrderReturnDetailsDTO update(OrderReturnDetailsDTO orderReturnDetailsDTO);

    /**
     * Partially updates a orderReturnDetails.
     *
     * @param orderReturnDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrderReturnDetailsDTO> partialUpdate(OrderReturnDetailsDTO orderReturnDetailsDTO);

    /**
     * Get all the orderReturnDetails.
     *
     * @return the list of entities.
     */
    List<OrderReturnDetailsDTO> findAll();

    /**
     * Get the "id" orderReturnDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderReturnDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" orderReturnDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
