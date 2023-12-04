package com.shoes.web.rest;

import com.shoes.repository.OrderReturnRepository;
import com.shoes.service.OrderReturnService;
import com.shoes.service.dto.OrderReturnDTO;
import com.shoes.service.dto.OrderReturnReqDTO;
import com.shoes.service.dto.OrderReturnSearchResDTO;
import com.shoes.service.dto.OrderSearchReqDTO;
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
 * REST controller for managing {@link com.shoes.domain.OrderReturn}.
 */
@RestController
@RequestMapping("/api")
public class OrderReturnResource {

    private final Logger log = LoggerFactory.getLogger(OrderReturnResource.class);

    private static final String ENTITY_NAME = "orderReturn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderReturnService orderReturnService;

    private final OrderReturnRepository orderReturnRepository;

    public OrderReturnResource(OrderReturnService orderReturnService, OrderReturnRepository orderReturnRepository) {
        this.orderReturnService = orderReturnService;
        this.orderReturnRepository = orderReturnRepository;
    }

    /**
     * {@code POST  /order-returns} : Create a new orderReturn.
     *
     * @param orderReturnDTO the orderReturnDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderReturnDTO, or with status {@code 400 (Bad Request)} if the orderReturn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-returns")
    public ResponseEntity<OrderReturnDTO> createOrderReturn(@RequestBody OrderReturnReqDTO orderReturnDTO) throws URISyntaxException {
        log.debug("REST request to save OrderReturn : {}", orderReturnDTO);
        if (orderReturnDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderReturn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderReturnDTO result = orderReturnService.save(orderReturnDTO);
        return ResponseEntity
            .created(new URI("/api/order-returns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-returns/:id} : Updates an existing orderReturn.
     *
     * @param id the id of the orderReturnDTO to save.
     * @param orderReturnDTO the orderReturnDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderReturnDTO,
     * or with status {@code 400 (Bad Request)} if the orderReturnDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderReturnDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-returns/{id}")
    public ResponseEntity<OrderReturnDTO> updateOrderReturn(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderReturnDTO orderReturnDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrderReturn : {}, {}", id, orderReturnDTO);
        if (orderReturnDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderReturnDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderReturnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderReturnDTO result = orderReturnService.update(orderReturnDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderReturnDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-returns/:id} : Partial updates given fields of an existing orderReturn, field will ignore if it is null
     *
     * @param id the id of the orderReturnDTO to save.
     * @param orderReturnDTO the orderReturnDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderReturnDTO,
     * or with status {@code 400 (Bad Request)} if the orderReturnDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderReturnDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderReturnDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-returns/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderReturnDTO> partialUpdateOrderReturn(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderReturnDTO orderReturnDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderReturn partially : {}, {}", id, orderReturnDTO);
        if (orderReturnDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderReturnDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderReturnRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderReturnDTO> result = orderReturnService.partialUpdate(orderReturnDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderReturnDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /order-returns} : get all the orderReturns.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderReturns in body.
     */
    @GetMapping("/order-returns")
    public List<OrderReturnDTO> getAllOrderReturns() {
        log.debug("REST request to get all OrderReturns");
        return orderReturnService.findAll();
    }

    @PostMapping("/order-returns/search")
    public List<OrderReturnSearchResDTO> getAllOrderReturns(@RequestBody OrderSearchReqDTO searchReqDTO) {
        log.debug("REST request to get all OrderReturns");
        return orderReturnService.search(searchReqDTO);
    }

    /**
     * {@code GET  /order-returns/:id} : get the "id" orderReturn.
     *
     * @param id the id of the orderReturnDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderReturnDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-returns/{id}")
    public ResponseEntity<OrderReturnDTO> getOrderReturn(@PathVariable Long id) {
        log.debug("REST request to get OrderReturn : {}", id);
        OrderReturnDTO orderReturnDTO = orderReturnService.findOne(id);
        return ResponseEntity.ok(orderReturnDTO);
    }

    /**
     * {@code DELETE  /order-returns/:id} : delete the "id" orderReturn.
     *
     * @param id the id of the orderReturnDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-returns/{id}")
    public ResponseEntity<Void> deleteOrderReturn(@PathVariable Long id) {
        log.debug("REST request to delete OrderReturn : {}", id);
        orderReturnService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
