package com.shoes.web.rest;

import com.shoes.repository.SizeRepository;
import com.shoes.service.SizeService;
import com.shoes.service.dto.SizeDTO;
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
 * REST controller for managing {@link com.shoes.domain.Size}.
 */
@RestController
@RequestMapping("/api")
public class SizeResource {

    private final Logger log = LoggerFactory.getLogger(SizeResource.class);

    private static final String ENTITY_NAME = "size";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SizeService sizeService;

    private final SizeRepository sizeRepository;

    public SizeResource(SizeService sizeService, SizeRepository sizeRepository) {
        this.sizeService = sizeService;
        this.sizeRepository = sizeRepository;
    }

    /**
     * {@code POST  /sizes} : Create a new size.
     *
     * @param sizeDTO the sizeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sizeDTO, or with status {@code 400 (Bad Request)} if the size has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sizes")
    public ResponseEntity<SizeDTO> createSize(@RequestBody SizeDTO sizeDTO) throws URISyntaxException {
        log.debug("REST request to save Size : {}", sizeDTO);
        if (sizeDTO.getId() != null) {
            throw new BadRequestAlertException("A new size cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SizeDTO result = sizeService.save(sizeDTO);
        return ResponseEntity
            .created(new URI("/api/sizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sizes/:id} : Updates an existing size.
     *
     * @param id the id of the sizeDTO to save.
     * @param sizeDTO the sizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sizeDTO,
     * or with status {@code 400 (Bad Request)} if the sizeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sizes/{id}")
    public ResponseEntity<SizeDTO> updateSize(@PathVariable(value = "id", required = false) final Long id, @RequestBody SizeDTO sizeDTO)
        throws URISyntaxException {
        log.debug("REST request to update Size : {}, {}", id, sizeDTO);
        if (sizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sizeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sizeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SizeDTO result = sizeService.update(sizeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sizes/:id} : Partial updates given fields of an existing size, field will ignore if it is null
     *
     * @param id the id of the sizeDTO to save.
     * @param sizeDTO the sizeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sizeDTO,
     * or with status {@code 400 (Bad Request)} if the sizeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sizeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sizeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sizes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SizeDTO> partialUpdateSize(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SizeDTO sizeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Size partially : {}, {}", id, sizeDTO);
        if (sizeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sizeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sizeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SizeDTO> result = sizeService.partialUpdate(sizeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sizeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sizes} : get all the sizes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sizes in body.
     */
    @GetMapping("/sizes")
    public ResponseEntity<List<SizeDTO>> getAllSizes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Sizes");
        Page<SizeDTO> page = sizeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
    @GetMapping("/sizes/removed")
    public ResponseEntity<List<SizeDTO>> getAllDelete(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Sizes");
        Page<SizeDTO> page = sizeService.findDelete(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sizes/:id} : get the "id" size.
     *
     * @param id the id of the sizeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sizeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sizes/{id}")
    public ResponseEntity<SizeDTO> getSize(@PathVariable Long id) {
        log.debug("REST request to get Size : {}", id);
        Optional<SizeDTO> sizeDTO = sizeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sizeDTO);
    }

    /**
     * {@code DELETE  /sizes/:id} : delete the "id" size.
     *
     * @param id the id of the sizeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sizes/{id}")
    public ResponseEntity<Void> deleteSize(@PathVariable Long id) {
        log.debug("REST request to delete Size : {}", id);
        sizeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
