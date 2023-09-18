package com.shoes.web.rest;

import com.shoes.repository.ShoesCategoryRepository;
import com.shoes.service.ShoesCategoryService;
import com.shoes.service.dto.ShoesCategoryDTO;
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
 * REST controller for managing {@link com.shoes.domain.ShoesCategory}.
 */
@RestController
@RequestMapping("/api")
public class ShoesCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ShoesCategoryResource.class);

    private static final String ENTITY_NAME = "shoesCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShoesCategoryService shoesCategoryService;

    private final ShoesCategoryRepository shoesCategoryRepository;

    public ShoesCategoryResource(ShoesCategoryService shoesCategoryService, ShoesCategoryRepository shoesCategoryRepository) {
        this.shoesCategoryService = shoesCategoryService;
        this.shoesCategoryRepository = shoesCategoryRepository;
    }

    /**
     * {@code POST  /shoes-categories} : Create a new shoesCategory.
     *
     * @param shoesCategoryDTO the shoesCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shoesCategoryDTO, or with status {@code 400 (Bad Request)} if the shoesCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shoes-categories")
    public ResponseEntity<ShoesCategoryDTO> createShoesCategory(@RequestBody ShoesCategoryDTO shoesCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save ShoesCategory : {}", shoesCategoryDTO);
        if (shoesCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoesCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoesCategoryDTO result = shoesCategoryService.save(shoesCategoryDTO);
        return ResponseEntity
            .created(new URI("/api/shoes-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shoes-categories/:id} : Updates an existing shoesCategory.
     *
     * @param id the id of the shoesCategoryDTO to save.
     * @param shoesCategoryDTO the shoesCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoesCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the shoesCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shoesCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shoes-categories/{id}")
    public ResponseEntity<ShoesCategoryDTO> updateShoesCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ShoesCategoryDTO shoesCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShoesCategory : {}, {}", id, shoesCategoryDTO);
        if (shoesCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoesCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoesCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShoesCategoryDTO result = shoesCategoryService.update(shoesCategoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoesCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shoes-categories/:id} : Partial updates given fields of an existing shoesCategory, field will ignore if it is null
     *
     * @param id the id of the shoesCategoryDTO to save.
     * @param shoesCategoryDTO the shoesCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoesCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the shoesCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shoesCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shoesCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shoes-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShoesCategoryDTO> partialUpdateShoesCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ShoesCategoryDTO shoesCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShoesCategory partially : {}, {}", id, shoesCategoryDTO);
        if (shoesCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoesCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoesCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShoesCategoryDTO> result = shoesCategoryService.partialUpdate(shoesCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoesCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shoes-categories} : get all the shoesCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shoesCategories in body.
     */
    @GetMapping("/shoes-categories")
    public ResponseEntity<List<ShoesCategoryDTO>> getAllShoesCategories(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ShoesCategories");
        Page<ShoesCategoryDTO> page = shoesCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shoes-categories/:id} : get the "id" shoesCategory.
     *
     * @param id the id of the shoesCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shoesCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shoes-categories/{id}")
    public ResponseEntity<ShoesCategoryDTO> getShoesCategory(@PathVariable Long id) {
        log.debug("REST request to get ShoesCategory : {}", id);
        Optional<ShoesCategoryDTO> shoesCategoryDTO = shoesCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoesCategoryDTO);
    }

    /**
     * {@code DELETE  /shoes-categories/:id} : delete the "id" shoesCategory.
     *
     * @param id the id of the shoesCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shoes-categories/{id}")
    public ResponseEntity<Void> deleteShoesCategory(@PathVariable Long id) {
        log.debug("REST request to delete ShoesCategory : {}", id);
        shoesCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
