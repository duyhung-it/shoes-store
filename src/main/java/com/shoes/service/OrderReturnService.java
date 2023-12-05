package com.shoes.service;

import com.shoes.service.dto.*;
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
    OrderReturnDTO verify(VerifyOrderReturnDTO verifyOrderReturnDTO);
    OrderReturnDTO cancel(Long id);
    OrderReturnDTO finish(Long id);

    /**
     * Updates a orderReturn.
     *
     * @param orderReturnDTO the entity to update.
     * @return the persisted entity.
     */
    OrderReturnDTO update(OrderReturnDTO orderReturnDTO);
    List<OrderReturnSearchResDTO> search(OrderSearchReqDTO searchReqDTO);

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
    OrderReturnDTO findOne(Long id);

    /**
     * Delete the "id" orderReturn.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
