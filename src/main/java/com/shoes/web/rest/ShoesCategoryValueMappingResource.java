package com.shoes.web.rest;

import com.shoes.repository.ShoesCategoryValueMappingRepository;
import com.shoes.service.ShoesCategoryValueMappingService;
import com.shoes.service.dto.ShoesCategoryValueMappingDTO;
import com.shoes.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.shoes.domain.ShoesCategoryValueMapping}.
 */
@RestController
@RequestMapping("/api")
public class ShoesCategoryValueMappingResource {

    private final Logger log = LoggerFactory.getLogger(ShoesCategoryValueMappingResource.class);

    private static final String ENTITY_NAME = "shoesCategoryValueMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShoesCategoryValueMappingService shoesCategoryValueMappingService;

    private final ShoesCategoryValueMappingRepository shoesCategoryValueMappingRepository;

    public ShoesCategoryValueMappingResource(
        ShoesCategoryValueMappingService shoesCategoryValueMappingService,
        ShoesCategoryValueMappingRepository shoesCategoryValueMappingRepository
    ) {
        this.shoesCategoryValueMappingService = shoesCategoryValueMappingService;
        this.shoesCategoryValueMappingRepository = shoesCategoryValueMappingRepository;
    }

    /**
     * {@code POST  /shoes-category-value-mappings} : Create a new shoesCategoryValueMapping.
     *
     * @param shoesCategoryValueMappingDTO the shoesCategoryValueMappingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shoesCategoryValueMappingDTO, or with status {@code 400 (Bad Request)} if the shoesCategoryValueMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shoes-category-value-mappings")
    public ResponseEntity<ShoesCategoryValueMappingDTO> createShoesCategoryValueMapping(
        @RequestBody ShoesCategoryValueMappingDTO shoesCategoryValueMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ShoesCategoryValueMapping : {}", shoesCategoryValueMappingDTO);
        if (shoesCategoryValueMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoesCategoryValueMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoesCategoryValueMappingDTO result = shoesCategoryValueMappingService.save(shoesCategoryValueMappingDTO);
        return ResponseEntity
            .created(new URI("/api/shoes-category-value-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shoes-category-value-mappings/:id} : Updates an existing shoesCategoryValueMapping.
     *
     * @param id the id of the shoesCategoryValueMappingDTO to save.
     * @param shoesCategoryValueMappingDTO the shoesCategoryValueMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoesCategoryValueMappingDTO,
     * or with status {@code 400 (Bad Request)} if the shoesCategoryValueMappingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shoesCategoryValueMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shoes-category-value-mappings/{id}")
    public ResponseEntity<ShoesCategoryValueMappingDTO> updateShoesCategoryValueMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ShoesCategoryValueMappingDTO shoesCategoryValueMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShoesCategoryValueMapping : {}, {}", id, shoesCategoryValueMappingDTO);
        if (shoesCategoryValueMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoesCategoryValueMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoesCategoryValueMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShoesCategoryValueMappingDTO result = shoesCategoryValueMappingService.update(shoesCategoryValueMappingDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoesCategoryValueMappingDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code PATCH  /shoes-category-value-mappings/:id} : Partial updates given fields of an existing shoesCategoryValueMapping, field will ignore if it is null
     *
     * @param id the id of the shoesCategoryValueMappingDTO to save.
     * @param shoesCategoryValueMappingDTO the shoesCategoryValueMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoesCategoryValueMappingDTO,
     * or with status {@code 400 (Bad Request)} if the shoesCategoryValueMappingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shoesCategoryValueMappingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shoesCategoryValueMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shoes-category-value-mappings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShoesCategoryValueMappingDTO> partialUpdateShoesCategoryValueMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ShoesCategoryValueMappingDTO shoesCategoryValueMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShoesCategoryValueMapping partially : {}, {}", id, shoesCategoryValueMappingDTO);
        if (shoesCategoryValueMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoesCategoryValueMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoesCategoryValueMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShoesCategoryValueMappingDTO> result = shoesCategoryValueMappingService.partialUpdate(shoesCategoryValueMappingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoesCategoryValueMappingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shoes-category-value-mappings} : get all the shoesCategoryValueMappings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shoesCategoryValueMappings in body.
     */
    @GetMapping("/shoes-category-value-mappings")
    public ResponseEntity<List<ShoesCategoryValueMappingDTO>> getAllShoesCategoryValueMappings(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ShoesCategoryValueMappings");
        Page<ShoesCategoryValueMappingDTO> page = shoesCategoryValueMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shoes-category-value-mappings/:id} : get the "id" shoesCategoryValueMapping.
     *
     * @param id the id of the shoesCategoryValueMappingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shoesCategoryValueMappingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shoes-category-value-mappings/{id}")
    public ResponseEntity<ShoesCategoryValueMappingDTO> getShoesCategoryValueMapping(@PathVariable Long id) {
        log.debug("REST request to get ShoesCategoryValueMapping : {}", id);
        Optional<ShoesCategoryValueMappingDTO> shoesCategoryValueMappingDTO = shoesCategoryValueMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoesCategoryValueMappingDTO);
    }

    /**
     * {@code DELETE  /shoes-category-value-mappings/:id} : delete the "id" shoesCategoryValueMapping.
     *
     * @param id the id of the shoesCategoryValueMappingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shoes-category-value-mappings/{id}")
    public ResponseEntity<Void> deleteShoesCategoryValueMapping(@PathVariable Long id) {
        log.debug("REST request to delete ShoesCategoryValueMapping : {}", id);
        shoesCategoryValueMappingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
