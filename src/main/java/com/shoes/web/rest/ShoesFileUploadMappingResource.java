package com.shoes.web.rest;

import com.shoes.repository.ShoesFileUploadMappingRepository;
import com.shoes.service.ShoesFileUploadMappingService;
import com.shoes.service.dto.ShoesFileUploadMappingDTO;
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
 * REST controller for managing {@link com.shoes.domain.ShoesFileUploadMapping}.
 */
@RestController
@RequestMapping("/api")
public class ShoesFileUploadMappingResource {

    private final Logger log = LoggerFactory.getLogger(ShoesFileUploadMappingResource.class);

    private static final String ENTITY_NAME = "shoesFileUploadMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShoesFileUploadMappingService shoesFileUploadMappingService;

    private final ShoesFileUploadMappingRepository shoesFileUploadMappingRepository;

    public ShoesFileUploadMappingResource(
        ShoesFileUploadMappingService shoesFileUploadMappingService,
        ShoesFileUploadMappingRepository shoesFileUploadMappingRepository
    ) {
        this.shoesFileUploadMappingService = shoesFileUploadMappingService;
        this.shoesFileUploadMappingRepository = shoesFileUploadMappingRepository;
    }

    /**
     * {@code POST  /shoes-file-upload-mappings} : Create a new shoesFileUploadMapping.
     *
     * @param shoesFileUploadMappingDTO the shoesFileUploadMappingDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shoesFileUploadMappingDTO, or with status {@code 400 (Bad Request)} if the shoesFileUploadMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shoes-file-upload-mappings")
    public ResponseEntity<ShoesFileUploadMappingDTO> createShoesFileUploadMapping(
        @RequestBody ShoesFileUploadMappingDTO shoesFileUploadMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ShoesFileUploadMapping : {}", shoesFileUploadMappingDTO);
        if (shoesFileUploadMappingDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoesFileUploadMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoesFileUploadMappingDTO result = shoesFileUploadMappingService.save(shoesFileUploadMappingDTO);
        return ResponseEntity
            .created(new URI("/api/shoes-file-upload-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shoes-file-upload-mappings/:id} : Updates an existing shoesFileUploadMapping.
     *
     * @param id the id of the shoesFileUploadMappingDTO to save.
     * @param shoesFileUploadMappingDTO the shoesFileUploadMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoesFileUploadMappingDTO,
     * or with status {@code 400 (Bad Request)} if the shoesFileUploadMappingDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shoesFileUploadMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shoes-file-upload-mappings/{id}")
    public ResponseEntity<ShoesFileUploadMappingDTO> updateShoesFileUploadMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ShoesFileUploadMappingDTO shoesFileUploadMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShoesFileUploadMapping : {}, {}", id, shoesFileUploadMappingDTO);
        if (shoesFileUploadMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoesFileUploadMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoesFileUploadMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShoesFileUploadMappingDTO result = shoesFileUploadMappingService.update(shoesFileUploadMappingDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoesFileUploadMappingDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shoes-file-upload-mappings/:id} : Partial updates given fields of an existing shoesFileUploadMapping, field will ignore if it is null
     *
     * @param id the id of the shoesFileUploadMappingDTO to save.
     * @param shoesFileUploadMappingDTO the shoesFileUploadMappingDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoesFileUploadMappingDTO,
     * or with status {@code 400 (Bad Request)} if the shoesFileUploadMappingDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shoesFileUploadMappingDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shoesFileUploadMappingDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shoes-file-upload-mappings/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShoesFileUploadMappingDTO> partialUpdateShoesFileUploadMapping(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ShoesFileUploadMappingDTO shoesFileUploadMappingDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShoesFileUploadMapping partially : {}, {}", id, shoesFileUploadMappingDTO);
        if (shoesFileUploadMappingDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoesFileUploadMappingDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoesFileUploadMappingRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShoesFileUploadMappingDTO> result = shoesFileUploadMappingService.partialUpdate(shoesFileUploadMappingDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoesFileUploadMappingDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shoes-file-upload-mappings} : get all the shoesFileUploadMappings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shoesFileUploadMappings in body.
     */
    @GetMapping("/shoes-file-upload-mappings")
    public ResponseEntity<List<ShoesFileUploadMappingDTO>> getAllShoesFileUploadMappings(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ShoesFileUploadMappings");
        Page<ShoesFileUploadMappingDTO> page = shoesFileUploadMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shoes-file-upload-mappings/:id} : get the "id" shoesFileUploadMapping.
     *
     * @param id the id of the shoesFileUploadMappingDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shoesFileUploadMappingDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shoes-file-upload-mappings/{id}")
    public ResponseEntity<ShoesFileUploadMappingDTO> getShoesFileUploadMapping(@PathVariable Long id) {
        log.debug("REST request to get ShoesFileUploadMapping : {}", id);
        Optional<ShoesFileUploadMappingDTO> shoesFileUploadMappingDTO = shoesFileUploadMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoesFileUploadMappingDTO);
    }

    /**
     * {@code DELETE  /shoes-file-upload-mappings/:id} : delete the "id" shoesFileUploadMapping.
     *
     * @param id the id of the shoesFileUploadMappingDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shoes-file-upload-mappings/{id}")
    public ResponseEntity<Void> deleteShoesFileUploadMapping(@PathVariable Long id) {
        log.debug("REST request to delete ShoesFileUploadMapping : {}", id);
        shoesFileUploadMappingService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
