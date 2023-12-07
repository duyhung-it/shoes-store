package com.shoes.web.rest;

import com.shoes.repository.OrderReturnDetailsRepository;
import com.shoes.service.OrderReturnDetailsService;
import com.shoes.service.dto.OrderReturnDetailsDTO;
import com.shoes.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.shoes.domain.OrderReturnDetails}.
 */
@RestController
@RequestMapping("/api")
public class OrderReturnDetailsResource {

    private final Logger log = LoggerFactory.getLogger(OrderReturnDetailsResource.class);

    private static final String ENTITY_NAME = "orderReturnDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderReturnDetailsService orderReturnDetailsService;

    private final OrderReturnDetailsRepository orderReturnDetailsRepository;

    public OrderReturnDetailsResource(
        OrderReturnDetailsService orderReturnDetailsService,
        OrderReturnDetailsRepository orderReturnDetailsRepository
    ) {
        this.orderReturnDetailsService = orderReturnDetailsService;
        this.orderReturnDetailsRepository = orderReturnDetailsRepository;
    }

    /**
     * {@code POST  /order-return-details} : Create a new orderReturnDetails.
     *
     * @param orderReturnDetailsDTO the orderReturnDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderReturnDetailsDTO, or with status {@code 400 (Bad Request)} if the orderReturnDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-return-details")
    public ResponseEntity<OrderReturnDetailsDTO> createOrderReturnDetails(@RequestBody OrderReturnDetailsDTO orderReturnDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save OrderReturnDetails : {}", orderReturnDetailsDTO);
        if (orderReturnDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderReturnDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderReturnDetailsDTO result = orderReturnDetailsService.save(orderReturnDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/order-return-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-return-details/:id} : Updates an existing orderReturnDetails.
     *
     * @param id the id of the orderReturnDetailsDTO to save.
     * @param orderReturnDetailsDTO the orderReturnDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderReturnDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the orderReturnDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderReturnDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-return-details/{id}")
    public ResponseEntity<OrderReturnDetailsDTO> updateOrderReturnDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderReturnDetailsDTO orderReturnDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrderReturnDetails : {}, {}", id, orderReturnDetailsDTO);
        if (orderReturnDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderReturnDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderReturnDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderReturnDetailsDTO result = orderReturnDetailsService.update(orderReturnDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderReturnDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-return-details/:id} : Partial updates given fields of an existing orderReturnDetails, field will ignore if it is null
     *
     * @param id the id of the orderReturnDetailsDTO to save.
     * @param orderReturnDetailsDTO the orderReturnDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderReturnDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the orderReturnDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderReturnDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderReturnDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-return-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderReturnDetailsDTO> partialUpdateOrderReturnDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderReturnDetailsDTO orderReturnDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderReturnDetails partially : {}, {}", id, orderReturnDetailsDTO);
        if (orderReturnDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderReturnDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderReturnDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderReturnDetailsDTO> result = orderReturnDetailsService.partialUpdate(orderReturnDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderReturnDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /order-return-details} : get all the orderReturnDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderReturnDetails in body.
     */
    @GetMapping("/order-return-details")
    public List<OrderReturnDetailsDTO> getAllOrderReturnDetails() {
        log.debug("REST request to get all OrderReturnDetails");
        return orderReturnDetailsService.findAll();
    }

    /**
     * {@code GET  /order-return-details/:id} : get the "id" orderReturnDetails.
     *
     * @param id the id of the orderReturnDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderReturnDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-return-details/{id}")
    public ResponseEntity<OrderReturnDetailsDTO> getOrderReturnDetails(@PathVariable Long id) {
        log.debug("REST request to get OrderReturnDetails : {}", id);
        Optional<OrderReturnDetailsDTO> orderReturnDetailsDTO = orderReturnDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderReturnDetailsDTO);
    }

    /**
     * {@code DELETE  /order-return-details/:id} : delete the "id" orderReturnDetails.
     *
     * @param id the id of the orderReturnDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-return-details/{id}")
    public ResponseEntity<Void> deleteOrderReturnDetails(@PathVariable Long id) {
        log.debug("REST request to delete OrderReturnDetails : {}", id);
        orderReturnDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
