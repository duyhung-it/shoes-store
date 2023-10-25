package com.shoes.web.rest;

import com.shoes.repository.DiscountShoesDetailsRepository;
import com.shoes.service.DiscountShoesDetailsService;
import com.shoes.service.dto.DiscountShoesDetailsDTO;
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
 * REST controller for managing {@link com.shoes.domain.DiscountShoesDetails}.
 */
@RestController
@RequestMapping("/api")
public class DiscountShoesDetailsResource {

    private final Logger log = LoggerFactory.getLogger(DiscountShoesDetailsResource.class);

    private static final String ENTITY_NAME = "discountShoesDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DiscountShoesDetailsService discountShoesDetailsService;

    private final DiscountShoesDetailsRepository discountShoesDetailsRepository;

    public DiscountShoesDetailsResource(
        DiscountShoesDetailsService discountShoesDetailsService,
        DiscountShoesDetailsRepository discountShoesDetailsRepository
    ) {
        this.discountShoesDetailsService = discountShoesDetailsService;
        this.discountShoesDetailsRepository = discountShoesDetailsRepository;
    }

    /**
     * {@code POST  /discount-shoes-details} : Create a new discountShoesDetails.
     *
     * @param discountShoesDetailsDTO the discountShoesDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new discountShoesDetailsDTO, or with status {@code 400 (Bad Request)} if the discountShoesDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/discount-shoes-details")
    public ResponseEntity<DiscountShoesDetailsDTO> createDiscountShoesDetails(@RequestBody DiscountShoesDetailsDTO discountShoesDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save DiscountShoesDetails : {}", discountShoesDetailsDTO);
        if (discountShoesDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new discountShoesDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiscountShoesDetailsDTO result = discountShoesDetailsService.save(discountShoesDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/discount-shoes-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /discount-shoes-details/:id} : Updates an existing discountShoesDetails.
     *
     * @param id the id of the discountShoesDetailsDTO to save.
     * @param discountShoesDetailsDTO the discountShoesDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discountShoesDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the discountShoesDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the discountShoesDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/discount-shoes-details/{id}")
    public ResponseEntity<DiscountShoesDetailsDTO> updateDiscountShoesDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DiscountShoesDetailsDTO discountShoesDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DiscountShoesDetails : {}, {}", id, discountShoesDetailsDTO);
        if (discountShoesDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, discountShoesDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!discountShoesDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DiscountShoesDetailsDTO result = discountShoesDetailsService.update(discountShoesDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, discountShoesDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /discount-shoes-details/:id} : Partial updates given fields of an existing discountShoesDetails, field will ignore if it is null
     *
     * @param id the id of the discountShoesDetailsDTO to save.
     * @param discountShoesDetailsDTO the discountShoesDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated discountShoesDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the discountShoesDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the discountShoesDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the discountShoesDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/discount-shoes-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DiscountShoesDetailsDTO> partialUpdateDiscountShoesDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DiscountShoesDetailsDTO discountShoesDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DiscountShoesDetails partially : {}, {}", id, discountShoesDetailsDTO);
        if (discountShoesDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, discountShoesDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!discountShoesDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DiscountShoesDetailsDTO> result = discountShoesDetailsService.partialUpdate(discountShoesDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, discountShoesDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /discount-shoes-details} : get all the discountShoesDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of discountShoesDetails in body.
     */
    @GetMapping("/discount-shoes-details")
    public List<DiscountShoesDetailsDTO> getAllDiscountShoesDetails() {
        log.debug("REST request to get all DiscountShoesDetails");
        return discountShoesDetailsService.findAll();
    }

    /**
     * {@code GET  /discount-shoes-details/:id} : get the "id" discountShoesDetails.
     *
     * @param id the id of the discountShoesDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the discountShoesDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/discount-shoes-details/{id}")
    public ResponseEntity<DiscountShoesDetailsDTO> getDiscountShoesDetails(@PathVariable Long id) {
        log.debug("REST request to get DiscountShoesDetails : {}", id);
        Optional<DiscountShoesDetailsDTO> discountShoesDetailsDTO = discountShoesDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(discountShoesDetailsDTO);
    }

    /**
     * {@code DELETE  /discount-shoes-details/:id} : delete the "id" discountShoesDetails.
     *
     * @param id the id of the discountShoesDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/discount-shoes-details/{id}")
    public ResponseEntity<Void> deleteDiscountShoesDetails(@PathVariable Long id) {
        log.debug("REST request to delete DiscountShoesDetails : {}", id);
        discountShoesDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
