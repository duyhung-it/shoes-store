package com.shoes.service;

import com.shoes.domain.Order;
import com.shoes.service.dto.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.shoes.domain.Order}.
 */
public interface OrderService {
    /**
     * Save a order.
     *
     * @param orderDTO the entity to save.
     * @return the persisted entity.
     */
    OrderDTO save(OrderCreateDTO orderDTO);

    /**
     * Updates a order.
     *
     * @param orderDTO the entity to update.
     * @return the persisted entity.
     */
    OrderDTO update(OrderDTO orderDTO);

    /**
     * Partially updates a order.
     *
     * @param orderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrderDTO> partialUpdate(OrderDTO orderDTO);

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OrderDTO> findAll(Pageable pageable);

    /**
     * Get the "id" order.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    OrderResDTO findOne(Long id);

    /**
     * Delete the "id" order.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<OrderDTO> getOrderByOwnerId(Long id, Pageable pageable);

    List<OrderSearchResDTO> search(OrderSearchReqDTO searchReqDTO);

    OrderDTO updateStatus(Long idOrder);
    Map<Integer, Integer> getQuantityPerOrderStatus();
    void verifyOrder(List<Long> orderId);
    void cancelOrder(List<Long> orderId);
    byte[] getMailVerify(Long orderId);
    byte[] getCancelOrderMail(Long orderId);
    List<Order> getOrderByStatusAndOwnerLogin(Integer status, String login);
    Order getOrderByCode(String code);
}
