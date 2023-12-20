package com.shoes.service;

import com.shoes.domain.Order;
import com.shoes.service.dto.PaymentDTO;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.shoes.domain.Payment}.
 */
public interface PaymentService {
    /**
     * Save a payment.
     *
     * @param paymentDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentDTO save(PaymentDTO paymentDTO);

    /**
     * Updates a payment.
     *
     * @param paymentDTO the entity to update.
     * @return the persisted entity.
     */
    PaymentDTO update(PaymentDTO paymentDTO);

    /**
     * Partially updates a payment.
     *
     * @param paymentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentDTO> partialUpdate(PaymentDTO paymentDTO);

    /**
     * Get all the payments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PaymentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" payment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentDTO> findOne(Long id);

    /**
     * Delete the "id" payment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    String createPayment(
        BigDecimal price,
        String receivedBy,
        String phone,
        String email,
        String address,
        Integer province,
        Integer district,
        Integer ward,
        BigDecimal shipPrice,
        String idOwner,
        String arrSanPham,
        String arrQuantity,
        String arrPriceDiscount
    ) throws UnsupportedEncodingException;

    int orderReturn(HttpServletRequest request);

    Order payCallBack(HttpServletRequest request, HttpServletResponse response);
}
