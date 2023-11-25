package com.shoes.web.rest;

import com.shoes.repository.FeedBackRepository;
import com.shoes.service.FeedBackService;
import com.shoes.service.dto.FeedBackDTO;
import com.shoes.service.dto.ShoesFeedBackDTO;
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
 * REST controller for managing {@link com.shoes.domain.FeedBack}.
 */
@RestController
@RequestMapping("/api")
public class FeedBackResource {

    private final Logger log = LoggerFactory.getLogger(FeedBackResource.class);

    private static final String ENTITY_NAME = "feedBack";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeedBackService feedBackService;

    private final FeedBackRepository feedBackRepository;

    public FeedBackResource(FeedBackService feedBackService, FeedBackRepository feedBackRepository) {
        this.feedBackService = feedBackService;
        this.feedBackRepository = feedBackRepository;
    }

    /**
     * {@code POST  /feed-backs} : Create a new feedBack.
     *
     * @param feedBackDTO the feedBackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedBackDTO, or with status {@code 400 (Bad Request)} if the feedBack has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feed-backs")
    public ResponseEntity<FeedBackDTO> createFeedBack(@RequestBody FeedBackDTO feedBackDTO) throws URISyntaxException {
        log.debug("REST request to save FeedBack : {}", feedBackDTO);
        if (feedBackDTO.getId() != null) {
            throw new BadRequestAlertException("A new feedBack cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FeedBackDTO result = feedBackService.save(feedBackDTO);
        return ResponseEntity
            .created(new URI("/api/feed-backs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feed-backs/:id} : Updates an existing feedBack.
     *
     * @param id the id of the feedBackDTO to save.
     * @param feedBackDTO the feedBackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedBackDTO,
     * or with status {@code 400 (Bad Request)} if the feedBackDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feedBackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feed-backs/{id}")
    public ResponseEntity<FeedBackDTO> updateFeedBack(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FeedBackDTO feedBackDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FeedBack : {}, {}", id, feedBackDTO);
        if (feedBackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedBackDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedBackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FeedBackDTO result = feedBackService.update(feedBackDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedBackDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /feed-backs/:id} : Partial updates given fields of an existing feedBack, field will ignore if it is null
     *
     * @param id the id of the feedBackDTO to save.
     * @param feedBackDTO the feedBackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedBackDTO,
     * or with status {@code 400 (Bad Request)} if the feedBackDTO is not valid,
     * or with status {@code 404 (Not Found)} if the feedBackDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the feedBackDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/feed-backs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FeedBackDTO> partialUpdateFeedBack(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FeedBackDTO feedBackDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FeedBack partially : {}, {}", id, feedBackDTO);
        if (feedBackDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, feedBackDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!feedBackRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FeedBackDTO> result = feedBackService.partialUpdate(feedBackDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, feedBackDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /feed-backs} : get all the feedBacks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedBacks in body.
     */
    @GetMapping("/feed-backs")
    public ResponseEntity<List<FeedBackDTO>> getAllFeedBacks(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FeedBacks");
        Page<FeedBackDTO> page = feedBackService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /feed-backs/:id} : get the "id" feedBack.
     *
     * @param id the id of the feedBackDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedBackDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feed-backs/{id}")
    public ResponseEntity<FeedBackDTO> getFeedBack(@PathVariable Long id) {
        log.debug("REST request to get FeedBack : {}", id);
        Optional<FeedBackDTO> feedBackDTO = feedBackService.findOne(id);
        return ResponseUtil.wrapOrNotFound(feedBackDTO);
    }

    /**
     * {@code DELETE  /feed-backs/:id} : delete the "id" feedBack.
     *
     * @param id the id of the feedBackDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feed-backs/{id}")
    public ResponseEntity<Void> deleteFeedBack(@PathVariable Long id) {
        log.debug("REST request to delete FeedBack : {}", id);
        feedBackService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/shop/feed-back")
    public ResponseEntity<List<ShoesFeedBackDTO>> getAllFeedBacks(@RequestParam Integer shid, @RequestParam Integer brid) {
        log.debug("REST request to get a page of FeedBacks");
        return ResponseEntity.ok().body(feedBackRepository.findAllFeedBackByShoesAndBrandDTO(shid, brid));
    }
}
