package com.shoes.web.rest;

import com.shoes.repository.ReturnShoesDetailsRepository;
import com.shoes.service.ReturnShoesDetailsService;
import com.shoes.service.dto.ReturnShoesDetailsDTO;
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
 * REST controller for managing {@link com.shoes.domain.ReturnShoesDetails}.
 */
@RestController
@RequestMapping("/api")
public class ReturnShoesDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ReturnShoesDetailsResource.class);

    private static final String ENTITY_NAME = "returnShoesDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReturnShoesDetailsService returnShoesDetailsService;

    private final ReturnShoesDetailsRepository returnShoesDetailsRepository;

    public ReturnShoesDetailsResource(
        ReturnShoesDetailsService returnShoesDetailsService,
        ReturnShoesDetailsRepository returnShoesDetailsRepository
    ) {
        this.returnShoesDetailsService = returnShoesDetailsService;
        this.returnShoesDetailsRepository = returnShoesDetailsRepository;
    }

    /**
     * {@code POST  /return-shoes-details} : Create a new returnShoesDetails.
     *
     * @param returnShoesDetailsDTO the returnShoesDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new returnShoesDetailsDTO, or with status {@code 400 (Bad Request)} if the returnShoesDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/return-shoes-details")
    public ResponseEntity<ReturnShoesDetailsDTO> createReturnShoesDetails(@RequestBody ReturnShoesDetailsDTO returnShoesDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReturnShoesDetails : {}", returnShoesDetailsDTO);
        if (returnShoesDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new returnShoesDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReturnShoesDetailsDTO result = returnShoesDetailsService.save(returnShoesDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/return-shoes-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /return-shoes-details/:id} : Updates an existing returnShoesDetails.
     *
     * @param id the id of the returnShoesDetailsDTO to save.
     * @param returnShoesDetailsDTO the returnShoesDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated returnShoesDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the returnShoesDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the returnShoesDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/return-shoes-details/{id}")
    public ResponseEntity<ReturnShoesDetailsDTO> updateReturnShoesDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReturnShoesDetailsDTO returnShoesDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReturnShoesDetails : {}, {}", id, returnShoesDetailsDTO);
        if (returnShoesDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, returnShoesDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!returnShoesDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReturnShoesDetailsDTO result = returnShoesDetailsService.update(returnShoesDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, returnShoesDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /return-shoes-details/:id} : Partial updates given fields of an existing returnShoesDetails, field will ignore if it is null
     *
     * @param id the id of the returnShoesDetailsDTO to save.
     * @param returnShoesDetailsDTO the returnShoesDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated returnShoesDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the returnShoesDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the returnShoesDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the returnShoesDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/return-shoes-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReturnShoesDetailsDTO> partialUpdateReturnShoesDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ReturnShoesDetailsDTO returnShoesDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReturnShoesDetails partially : {}, {}", id, returnShoesDetailsDTO);
        if (returnShoesDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, returnShoesDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!returnShoesDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReturnShoesDetailsDTO> result = returnShoesDetailsService.partialUpdate(returnShoesDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, returnShoesDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /return-shoes-details} : get all the returnShoesDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of returnShoesDetails in body.
     */
    @GetMapping("/return-shoes-details")
    public List<ReturnShoesDetailsDTO> getAllReturnShoesDetails() {
        log.debug("REST request to get all ReturnShoesDetails");
        return returnShoesDetailsService.findAll();
    }

    /**
     * {@code GET  /return-shoes-details/:id} : get the "id" returnShoesDetails.
     *
     * @param id the id of the returnShoesDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the returnShoesDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/return-shoes-details/{id}")
    public ResponseEntity<ReturnShoesDetailsDTO> getReturnShoesDetails(@PathVariable Long id) {
        log.debug("REST request to get ReturnShoesDetails : {}", id);
        Optional<ReturnShoesDetailsDTO> returnShoesDetailsDTO = returnShoesDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(returnShoesDetailsDTO);
    }

    /**
     * {@code DELETE  /return-shoes-details/:id} : delete the "id" returnShoesDetails.
     *
     * @param id the id of the returnShoesDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/return-shoes-details/{id}")
    public ResponseEntity<Void> deleteReturnShoesDetails(@PathVariable Long id) {
        log.debug("REST request to delete ReturnShoesDetails : {}", id);
        returnShoesDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
