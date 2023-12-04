package com.shoes.service;

import com.shoes.service.dto.OrderReturnDTO;
import com.shoes.service.dto.OrderReturnReqDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.shoes.domain.OrderReturn}.
 */
public interface OrderReturnService {
    /**
     * Save a orderReturn.
     *
     * @param orderReturnDTO the entity to save.
     * @return the persisted entity.
     */
    OrderReturnDTO save(OrderReturnReqDTO orderReturnDTO);

    /**
     * Updates a orderReturn.
     *
     * @param orderReturnDTO the entity to update.
     * @return the persisted entity.
     */
    OrderReturnDTO update(OrderReturnDTO orderReturnDTO);

    /**
     * Partially updates a orderReturn.
     *
     * @param orderReturnDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrderReturnDTO> partialUpdate(OrderReturnDTO orderReturnDTO);

    /**
     * Get all the orderReturns.
     *
     * @return the list of entities.
     */
    List<OrderReturnDTO> findAll();

    /**
     * Get the "id" orderReturn.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderReturnDTO> findOne(Long id);

    /**
     * Delete the "id" orderReturn.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
