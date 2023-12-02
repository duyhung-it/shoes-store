package com.shoes.web.rest;

import com.shoes.repository.ReturnOrderDetailsRepository;
import com.shoes.service.ReturnOrderDetailsService;
import com.shoes.service.dto.ReturnOrderDetailsDTO;
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
 * REST controller for managing {@link com.shoes.domain.ReturnOrderDetails}.
 */
@RestController
@RequestMapping("/api")
public class ReturnOrderDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ReturnOrderDetailsResource.class);

    private static final String ENTITY_NAME = "returnOrderDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReturnOrderDetailsService returnOrderDetailsService;

    private final ReturnOrderDetailsRepository returnOrderDetailsRepository;

    public ReturnOrderDetailsResource(
        ReturnOrderDetailsService returnOrderDetailsService,
        ReturnOrderDetailsRepository returnOrderDetailsRepository
    ) {
        this.returnOrderDetailsService = returnOrderDetailsService;
        this.returnOrderDetailsRepository = returnOrderDetailsRepository;
    }

    /**
     * {@code POST  /return-order-details} : Create a new returnOrderDetails.
     *
     * @param returnOrderDetailsDTO the returnOrderDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new returnOrderDetailsDTO, or with status {@code 400 (Bad Request)} if the returnOrderDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/return-order-details")
    public ResponseEntity<ReturnOrderDetailsDTO> createReturnOrderDetails(@RequestBody ReturnOrderDetailsDTO returnOrderDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReturnOrderDetails : {}", returnOrderDetailsDTO);
        if (returnOrderDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new returnOrderDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReturnOrderDetailsDTO result = returnOrderDetailsService.save(returnOrderDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/return-order-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /return-order-details/:id} : Updates an existing returnOrderDetails.
     *
     * @param id the id of the returnOrderDetailsDTO to save.
     * @param returnOrderDetailsDTO the returnOrderDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated returnOrderDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the returnOrderDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the returnOrderDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/return-order-details/{id}")
    public ResponseEntity<ReturnOrderDetailsDTO> updateReturnOrderDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReturnOrderDetailsDTO returnOrderDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReturnOrderDetails : {}, {}", id, returnOrderDetailsDTO);
        if (returnOrderDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, returnOrderDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!returnOrderDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReturnOrderDetailsDTO result = returnOrderDetailsService.update(returnOrderDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, returnOrderDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /return-order-details/:id} : Partial updates given fields of an existing returnOrderDetails, field will ignore if it is null
     *
     * @param id the id of the returnOrderDetailsDTO to save.
     * @param returnOrderDetailsDTO the returnOrderDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated returnOrderDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the returnOrderDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the returnOrderDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the returnOrderDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/return-order-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReturnOrderDetailsDTO> partialUpdateReturnOrderDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReturnOrderDetailsDTO returnOrderDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReturnOrderDetails partially : {}, {}", id, returnOrderDetailsDTO);
        if (returnOrderDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, returnOrderDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!returnOrderDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReturnOrderDetailsDTO> result = returnOrderDetailsService.partialUpdate(returnOrderDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, returnOrderDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /return-order-details} : get all the returnOrderDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of returnOrderDetails in body.
     */
    @GetMapping("/return-order-details")
    public List<ReturnOrderDetailsDTO> getAllReturnOrderDetails() {
        log.debug("REST request to get all ReturnOrderDetails");
        return returnOrderDetailsService.findAll();
    }

    /**
     * {@code GET  /return-order-details/:id} : get the "id" returnOrderDetails.
     *
     * @param id the id of the returnOrderDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the returnOrderDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/return-order-details/{id}")
    public ResponseEntity<ReturnOrderDetailsDTO> getReturnOrderDetails(@PathVariable Long id) {
        log.debug("REST request to get ReturnOrderDetails : {}", id);
        Optional<ReturnOrderDetailsDTO> returnOrderDetailsDTO = returnOrderDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(returnOrderDetailsDTO);
    }

    /**
     * {@code DELETE  /return-order-details/:id} : delete the "id" returnOrderDetails.
     *
     * @param id the id of the returnOrderDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/return-order-details/{id}")
    public ResponseEntity<Void> deleteReturnOrderDetails(@PathVariable Long id) {
        log.debug("REST request to delete ReturnOrderDetails : {}", id);
        returnOrderDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
