package com.shoes.web.rest;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.shoes.config.Constants;
import com.shoes.repository.ShoesCategoryRepository;
import com.shoes.service.ShoesCategoryService;
import com.shoes.service.dto.*;
import com.shoes.util.AWSS3Util;
import com.shoes.util.DataUtils;
import com.shoes.web.rest.errors.BadRequestAlertException;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;

/**
 * REST controller for managing {@link com.shoes.domain.ShoesCategory}.
 */
@RestController
@RequestMapping("/api/shoes-categories")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ShoesCategoryResource {

    private final Logger log = LoggerFactory.getLogger(ShoesCategoryResource.class);

    private static final String ENTITY_NAME = "shoesCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShoesCategoryService shoesCategoryService;

    private final ShoesCategoryRepository shoesCategoryRepository;

    /**
     * {@code POST  } : Create a new shoesCategory.
     *
     * @param shoesCategoryDTO the shoesCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shoesCategoryDTO, or with status {@code 400 (Bad Request)} if the shoesCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ShoesCategoryDTO> createShoesCategory(@RequestBody @Valid ShoesCategoryDTO shoesCategoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save ShoesCategory : {}", shoesCategoryDTO);
        if (shoesCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new shoesCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShoesCategoryDTO result = shoesCategoryService.save(shoesCategoryDTO);
        return ResponseEntity.created(new URI("/api/")).body(result);
    }

    /**
     * {@code PUT  /:id} : Updates an existing shoesCategory.
     *
     * @param id the id of the shoesCategoryDTO to save.
     * @param shoesCategoryDTO the shoesCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shoesCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the shoesCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shoesCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ShoesCategoryDTO> updateShoesCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody @Valid ShoesCategoryUpdateDTO shoesCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShoesCategory : {}, {}", id, shoesCategoryDTO);

        ShoesCategoryDTO result = shoesCategoryService.update(shoesCategoryDTO, id);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  } : get all the shoesCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shoesCategories in body.
     */
    @GetMapping("")
    public ResponseEntity<Page<ShoesCategorySearchResDTO>> getAllShoesCategories(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ShoesCategories");
        Page<ShoesCategorySearchResDTO> page = shoesCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }

    /**
     * {@code GET  /:id} : get the "id" shoesCategory.
     *
     * @param id the id of the shoesCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shoesCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ShoesCategoryDTO> getShoesCategory(@PathVariable Long id) {
        log.debug("REST request to get ShoesCategory : {}", id);
        ShoesCategoryDTO shoesCategoryDTO = shoesCategoryService.findOne(id);
        return ResponseEntity.ok(shoesCategoryDTO);
    }

    /**
     * {@code DELETE  /:id} : delete the "id" shoesCategory.
     *
     * @param id the id of the shoesCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoesCategory(@PathVariable Long id) {
        log.debug("REST request to delete ShoesCategory : {}", id);
        shoesCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    @PostMapping("/search")
    public ResponseEntity<Page<ShoesCategorySearchResDTO>> search(
        @RequestBody ShoesCategorySearchReqDTO shoescategorySearchReqDTO,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to search ShoesCategory");
        Page<ShoesCategorySearchResDTO> shoesCategoryDTOPage = shoesCategoryService.search(shoescategorySearchReqDTO, pageable);
        return ResponseEntity.ok(shoesCategoryDTOPage);
    }

    @PostMapping("/upload")
    public String publicEntity(@ModelAttribute ObjectTest objectTest) {
        File file = null;
        try {
            file = DataUtils.multipartFileToFile(objectTest.getFile());
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        new AWSS3Util().uploadPhoto("images/" + Constants.KEY_UPLOAD + objectTest.getFile().getOriginalFilename(), file);
        return "uploadsucces";
    }
}
