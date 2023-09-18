package com.shoes.web.rest;

import com.shoes.repository.CartDetailsRepository;
import com.shoes.service.CartDetailsService;
import com.shoes.service.dto.CartDetailsDTO;
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
 * REST controller for managing {@link com.shoes.domain.CartDetails}.
 */
@RestController
@RequestMapping("/api")
public class CartDetailsResource {

    private final Logger log = LoggerFactory.getLogger(CartDetailsResource.class);

    private static final String ENTITY_NAME = "cartDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CartDetailsService cartDetailsService;

    private final CartDetailsRepository cartDetailsRepository;

    public CartDetailsResource(CartDetailsService cartDetailsService, CartDetailsRepository cartDetailsRepository) {
        this.cartDetailsService = cartDetailsService;
        this.cartDetailsRepository = cartDetailsRepository;
    }

    /**
     * {@code POST  /cart-details} : Create a new cartDetails.
     *
     * @param cartDetailsDTO the cartDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cartDetailsDTO, or with status {@code 400 (Bad Request)} if the cartDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cart-details")
    public ResponseEntity<CartDetailsDTO> createCartDetails(@RequestBody CartDetailsDTO cartDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save CartDetails : {}", cartDetailsDTO);
        if (cartDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new cartDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CartDetailsDTO result = cartDetailsService.save(cartDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/cart-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cart-details/:id} : Updates an existing cartDetails.
     *
     * @param id the id of the cartDetailsDTO to save.
     * @param cartDetailsDTO the cartDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the cartDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cartDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cart-details/{id}")
    public ResponseEntity<CartDetailsDTO> updateCartDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CartDetailsDTO cartDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CartDetails : {}, {}", id, cartDetailsDTO);
        if (cartDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cartDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cartDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CartDetailsDTO result = cartDetailsService.update(cartDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cartDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cart-details/:id} : Partial updates given fields of an existing cartDetails, field will ignore if it is null
     *
     * @param id the id of the cartDetailsDTO to save.
     * @param cartDetailsDTO the cartDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the cartDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cartDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cartDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cart-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CartDetailsDTO> partialUpdateCartDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CartDetailsDTO cartDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CartDetails partially : {}, {}", id, cartDetailsDTO);
        if (cartDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cartDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cartDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CartDetailsDTO> result = cartDetailsService.partialUpdate(cartDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cartDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cart-details} : get all the cartDetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cartDetails in body.
     */
    @GetMapping("/cart-details")
    public ResponseEntity<List<CartDetailsDTO>> getAllCartDetails(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CartDetails");
        Page<CartDetailsDTO> page = cartDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cart-details/:id} : get the "id" cartDetails.
     *
     * @param id the id of the cartDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cartDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cart-details/{id}")
    public ResponseEntity<CartDetailsDTO> getCartDetails(@PathVariable Long id) {
        log.debug("REST request to get CartDetails : {}", id);
        Optional<CartDetailsDTO> cartDetailsDTO = cartDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cartDetailsDTO);
    }

    /**
     * {@code DELETE  /cart-details/:id} : delete the "id" cartDetails.
     *
     * @param id the id of the cartDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cart-details/{id}")
    public ResponseEntity<Void> deleteCartDetails(@PathVariable Long id) {
        log.debug("REST request to delete CartDetails : {}", id);
        cartDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
