package com.shoes.service;

import com.shoes.service.dto.OrderDetailDTOInterface;
import com.shoes.service.dto.OrderDetailsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.shoes.domain.OrderDetails}.
 */
public interface OrderDetailsService {
    /**
     * Save a orderDetails.
     *
     * @param orderDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    OrderDetailsDTO save(OrderDetailsDTO orderDetailsDTO);

    /**
     * Updates a orderDetails.
     *
     * @param orderDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    OrderDetailsDTO update(OrderDetailsDTO orderDetailsDTO);

    /**
     * Partially updates a orderDetails.
     *
     * @param orderDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrderDetailsDTO> partialUpdate(OrderDetailsDTO orderDetailsDTO);

    /**
     * Get all the orderDetails.
     *
     * @return the list of entities.
     */
    List<OrderDetailsDTO> findAll();

    /**
     * Get the "id" orderDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" orderDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    List<OrderDetailDTOInterface> findAllByOrder_Id(Long id);
}
