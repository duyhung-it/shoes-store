package com.shoes.web.rest;

import com.shoes.repository.ShoesCategoryValueRepository;
import com.shoes.service.ShoesCategoryValueService;
import com.shoes.service.dto.ShoesCategoryValueDTO;
import com.shoes.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
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
 * REST controller for managing {@link com.shoes.domain.ShoesCategoryValue}.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShoesCategoryValueResource {

    private final Logger log = LoggerFactory.getLogger(ShoesCategoryValueResource.class);

    private static final String ENTITY_NAME = "shoesCategoryValue";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShoesCategoryValueService shoesCategoryValueService;

    private final ShoesCategoryValueRepository shoesCategoryValueRepository;

    /**
     * {@code POST  /shoes-category-values} : Create a new shoesCategoryValue.
     *
     * @param shoesCategoryValueDTO the shoesCategoryValueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shoesCategoryValueDTO, or with status {@code 400 (Bad Request)} if the shoesCategoryValue has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shoes-category-values")
    public ResponseEntity<ShoesCategoryValueDTO> createShoesCategoryValue(@RequestBody ShoesCategoryValueDTO shoesCategoryValueDTO)
        throws URISyntaxException {
        log.debug("REST request to save ShoesCategoryValue : {}", shoesCategoryValueDTO);
        if (shoesCategoryValueDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoesCategoryValue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(shoesCategoryValueDTO.getCategory())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        ShoesCategoryValueDTO result = shoesCategoryValueService.save(shoesCategoryValueDTO);
        return ResponseEntity
            .created(new URI("/api/shoes-category-values/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shoes-category-values/:id} : Updates an existing shoesCategoryValue.
     *
     * @param id the id of the shoesCategoryValueDTO to save.
     * @param shoesCategoryValueDTO the shoesCategoryValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoesCategoryValueDTO,
     * or with status {@code 400 (Bad Request)} if the shoesCategoryValueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shoesCategoryValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shoes-category-values/{id}")
    public ResponseEntity<ShoesCategoryValueDTO> updateShoesCategoryValue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ShoesCategoryValueDTO shoesCategoryValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShoesCategoryValue : {}, {}", id, shoesCategoryValueDTO);
        if (shoesCategoryValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoesCategoryValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoesCategoryValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShoesCategoryValueDTO result = shoesCategoryValueService.update(shoesCategoryValueDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoesCategoryValueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shoes-category-values/:id} : Partial updates given fields of an existing shoesCategoryValue, field will ignore if it is null
     *
     * @param id the id of the shoesCategoryValueDTO to save.
     * @param shoesCategoryValueDTO the shoesCategoryValueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoesCategoryValueDTO,
     * or with status {@code 400 (Bad Request)} if the shoesCategoryValueDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shoesCategoryValueDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shoesCategoryValueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shoes-category-values/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShoesCategoryValueDTO> partialUpdateShoesCategoryValue(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ShoesCategoryValueDTO shoesCategoryValueDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShoesCategoryValue partially : {}, {}", id, shoesCategoryValueDTO);
        if (shoesCategoryValueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoesCategoryValueDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoesCategoryValueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShoesCategoryValueDTO> result = shoesCategoryValueService.partialUpdate(shoesCategoryValueDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoesCategoryValueDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shoes-category-values} : get all the shoesCategoryValues.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shoesCategoryValues in body.
     */
    @GetMapping("/shoes-category-values")
    public ResponseEntity<List<ShoesCategoryValueDTO>> getAllShoesCategoryValues(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ShoesCategoryValues");
        Page<ShoesCategoryValueDTO> page = shoesCategoryValueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/shoes-category-values/category/{id}")
    public ResponseEntity<List<ShoesCategoryValueDTO>> getAllByShoesCategory(@PathVariable("id") Long idCategory) {
        log.debug("REST request to get a page of ShoesCategoryValues");
        List<ShoesCategoryValueDTO> shoesCategoryValueDTOList = shoesCategoryValueService.findAllByShoesCategory(idCategory);
        return ResponseEntity.ok().body(shoesCategoryValueDTOList);
    }

    /**
     * {@code GET  /shoes-category-values/:id} : get the "id" shoesCategoryValue.
     *
     * @param id the id of the shoesCategoryValueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shoesCategoryValueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shoes-category-values/{id}")
    public ResponseEntity<ShoesCategoryValueDTO> getShoesCategoryValue(@PathVariable Long id) {
        log.debug("REST request to get ShoesCategoryValue : {}", id);
        Optional<ShoesCategoryValueDTO> shoesCategoryValueDTO = shoesCategoryValueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoesCategoryValueDTO);
    }

    /**
     * {@code DELETE  /shoes-category-values/:id} : delete the "id" shoesCategoryValue.
     *
     * @param id the id of the shoesCategoryValueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shoes-category-values/{id}")
    public ResponseEntity<Void> deleteShoesCategoryValue(@PathVariable Long id) {
        log.debug("REST request to delete ShoesCategoryValue : {}", id);
        shoesCategoryValueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
