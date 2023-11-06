package com.shoes.web.rest;

import com.shoes.repository.ShoesRepository;
import com.shoes.service.ShoesService;
import com.shoes.service.dto.ShoesDTO;
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
 * REST controller for managing {@link com.shoes.domain.Shoes}.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShoesResource {

    private final Logger log = LoggerFactory.getLogger(ShoesResource.class);

    private static final String ENTITY_NAME = "shoes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShoesService shoesService;

    private final ShoesRepository shoesRepository;

    /**
     * {@code POST  /shoes} : Create a new shoes.
     *
     * @param shoesDTO the shoesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shoesDTO, or with status {@code 400 (Bad Request)} if the shoes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shoes")
    public ResponseEntity<ShoesDTO> createShoes(@RequestBody ShoesDTO shoesDTO) throws URISyntaxException {
        log.debug("REST request to save Shoes : {}", shoesDTO);
        if (shoesDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoesDTO result = shoesService.save(shoesDTO);
        return ResponseEntity
            .created(new URI("/api/shoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shoes/:id} : Updates an existing shoes.
     *
     * @param id the id of the shoesDTO to save.
     * @param shoesDTO the shoesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoesDTO,
     * or with status {@code 400 (Bad Request)} if the shoesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shoesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shoes/{id}")
    public ResponseEntity<ShoesDTO> updateShoes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ShoesDTO shoesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Shoes : {}, {}", id, shoesDTO);
        if (shoesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShoesDTO result = shoesService.update(shoesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shoes/:id} : Partial updates given fields of an existing shoes, field will ignore if it is null
     *
     * @param id the id of the shoesDTO to save.
     * @param shoesDTO the shoesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoesDTO,
     * or with status {@code 400 (Bad Request)} if the shoesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shoesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shoesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shoes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShoesDTO> partialUpdateShoes(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ShoesDTO shoesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Shoes partially : {}, {}", id, shoesDTO);
        if (shoesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shoesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shoesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShoesDTO> result = shoesService.partialUpdate(shoesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, shoesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shoes} : get all the shoes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shoes in body.
     */
    @GetMapping("/shoes")
    public ResponseEntity<List<ShoesDTO>> getAllShoes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Shoes");
        List<ShoesDTO> page = shoesService.findAll(pageable);
        return ResponseEntity.ok(page);
    }

    /**
     * {@code GET  /shoes/:id} : get the "id" shoes.
     *
     * @param id the id of the shoesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shoesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shoes/{id}")
    public ResponseEntity<ShoesDTO> getShoes(@PathVariable Long id) {
        log.debug("REST request to get Shoes : {}", id);
        Optional<ShoesDTO> shoesDTO = shoesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shoesDTO);
    }

    /**
     * {@code DELETE  /shoes/:id} : delete the "id" shoes.
     *
     * @param id the id of the shoesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shoes/{id}")
    public ResponseEntity<Void> deleteShoes(@PathVariable Long id) {
        log.debug("REST request to delete Shoes : {}", id);
        shoesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
