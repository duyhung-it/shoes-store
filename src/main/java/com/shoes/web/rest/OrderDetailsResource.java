package com.shoes.web.rest;

import com.shoes.domain.Order;
import com.shoes.domain.OrderDetails;
import com.shoes.repository.OrderDetailsRepository;
import com.shoes.service.OrderDetailsService;
import com.shoes.service.OrderService;
import com.shoes.service.dto.OrderDetailDTOInterface;
import com.shoes.service.dto.OrderDetailsDTO;
import com.shoes.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.shoes.domain.OrderDetails}.
 */
@RestController
@RequestMapping("/api")
public class OrderDetailsResource {

    private final Logger log = LoggerFactory.getLogger(OrderDetailsResource.class);

    private static final String ENTITY_NAME = "orderDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderDetailsService orderDetailsService;
    private final OrderService orderService;
    private final OrderDetailsRepository orderDetailsRepository;

    public OrderDetailsResource(
        OrderDetailsService orderDetailsService,
        OrderService orderService,
        OrderDetailsRepository orderDetailsRepository
    ) {
        this.orderDetailsService = orderDetailsService;
        this.orderService = orderService;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    /**
     * {@code POST  /order-details} : Create a new orderDetails.
     *
     * @param orderDetailsDTO the orderDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderDetailsDTO, or with status {@code 400 (Bad Request)} if the orderDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-details")
    public ResponseEntity<OrderDetailsDTO> createOrderDetails(@RequestBody OrderDetailsDTO orderDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save OrderDetails : {}", orderDetailsDTO);
        if (orderDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderDetailsDTO result = orderDetailsService.save(orderDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/order-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-details/:id} : Updates an existing orderDetails.
     *
     * @param id the id of the orderDetailsDTO to save.
     * @param orderDetailsDTO the orderDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the orderDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-details/{id}")
    public ResponseEntity<OrderDetailsDTO> updateOrderDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderDetailsDTO orderDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrderDetails : {}, {}", id, orderDetailsDTO);
        if (orderDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderDetailsDTO result = orderDetailsService.update(orderDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-details/:id} : Partial updates given fields of an existing orderDetails, field will ignore if it is null
     *
     * @param id the id of the orderDetailsDTO to save.
     * @param orderDetailsDTO the orderDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the orderDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderDetailsDTO> partialUpdateOrderDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderDetailsDTO orderDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderDetails partially : {}, {}", id, orderDetailsDTO);
        if (orderDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderDetailsDTO> result = orderDetailsService.partialUpdate(orderDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /order-details} : get all the orderDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderDetails in body.
     */
    @GetMapping("/order-details")
    public List<OrderDetailsDTO> getAllOrderDetails() {
        log.debug("REST request to get all OrderDetails");
        return orderDetailsService.findAll();
    }

    /**
     * {@code GET  /order-details/:id} : get the "id" orderDetails.
     *
     * @param id the id of the orderDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-details/{id}")
    public ResponseEntity<OrderDetailsDTO> getOrderDetails(@PathVariable Long id) {
        log.debug("REST request to get OrderDetails : {}", id);
        Optional<OrderDetailsDTO> orderDetailsDTO = orderDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderDetailsDTO);
    }

    /**
     * {@code DELETE  /order-details/:id} : delete the "id" orderDetails.
     *
     * @param id the id of the orderDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-details/{id}")
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable Long id) {
        log.debug("REST request to delete OrderDetails : {}", id);
        orderDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @Transactional
    @GetMapping("/orders/{code}/details")
    public ResponseEntity<?> getOrderDetailsByOrderCode(@PathVariable String code) {
        Order order = orderService.getOrderByCode(code);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        List<OrderDetailDTOInterface> list = orderDetailsService.findAllByOrder_Id(order.getId());
        return ResponseEntity.ok(list);
    }
}
