package com.shoes.web.rest;

import com.shoes.domain.FileUpload;
import com.shoes.repository.FileUploadRepository;
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
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.shoes.domain.FileUpload}.
 */
@RestController
@RequestMapping("/api")
@Transactional
@RequiredArgsConstructor
public class FileUploadResource {

    private final Logger log = LoggerFactory.getLogger(FileUploadResource.class);

    private static final String ENTITY_NAME = "fileUpload";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileUploadRepository fileUploadRepository;

    /**
     * {@code POST  /file-uploads} : Create a new fileUpload.
     *
     * @param fileUpload the fileUpload to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileUpload, or with status {@code 400 (Bad Request)} if the fileUpload has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/file-uploads")
    public ResponseEntity<FileUpload> createFileUpload(@RequestBody FileUpload fileUpload) throws URISyntaxException {
        log.debug("REST request to save FileUpload : {}", fileUpload);
        if (fileUpload.getId() != null) {
            throw new BadRequestAlertException("A new fileUpload cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileUpload result = fileUploadRepository.save(fileUpload);
        return ResponseEntity
            .created(new URI("/api/file-uploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-uploads/:id} : Updates an existing fileUpload.
     *
     * @param id the id of the fileUpload to save.
     * @param fileUpload the fileUpload to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileUpload,
     * or with status {@code 400 (Bad Request)} if the fileUpload is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileUpload couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/file-uploads/{id}")
    public ResponseEntity<FileUpload> updateFileUpload(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FileUpload fileUpload
    ) throws URISyntaxException {
        log.debug("REST request to update FileUpload : {}, {}", id, fileUpload);
        if (fileUpload.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileUpload.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileUploadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FileUpload result = fileUploadRepository.save(fileUpload);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fileUpload.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /file-uploads/:id} : Partial updates given fields of an existing fileUpload, field will ignore if it is null
     *
     * @param id the id of the fileUpload to save.
     * @param fileUpload the fileUpload to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileUpload,
     * or with status {@code 400 (Bad Request)} if the fileUpload is not valid,
     * or with status {@code 404 (Not Found)} if the fileUpload is not found,
     * or with status {@code 500 (Internal Server Error)} if the fileUpload couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/file-uploads/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FileUpload> partialUpdateFileUpload(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FileUpload fileUpload
    ) throws URISyntaxException {
        log.debug("REST request to partial update FileUpload partially : {}, {}", id, fileUpload);
        if (fileUpload.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileUpload.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileUploadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FileUpload> result = fileUploadRepository
            .findById(fileUpload.getId())
            .map(existingFileUpload -> {
                if (fileUpload.getPath() != null) {
                    existingFileUpload.setPath(fileUpload.getPath());
                }
                if (fileUpload.getName() != null) {
                    existingFileUpload.setName(fileUpload.getName());
                }
                if (fileUpload.getStatus() != null) {
                    existingFileUpload.setStatus(fileUpload.getStatus());
                }
                if (fileUpload.getCreatedBy() != null) {
                    existingFileUpload.setCreatedBy(fileUpload.getCreatedBy());
                }
                if (fileUpload.getCreatedDate() != null) {
                    existingFileUpload.setCreatedDate(fileUpload.getCreatedDate());
                }
                if (fileUpload.getLastModifiedBy() != null) {
                    existingFileUpload.setLastModifiedBy(fileUpload.getLastModifiedBy());
                }
                if (fileUpload.getLastModifiedDate() != null) {
                    existingFileUpload.setLastModifiedDate(fileUpload.getLastModifiedDate());
                }

                return existingFileUpload;
            })
            .map(fileUploadRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fileUpload.getId().toString())
        );
    }

    /**
     * {@code GET  /file-uploads} : get all the fileUploads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileUploads in body.
     */
    @GetMapping("/file-uploads")
    public List<FileUpload> getAllFileUploads() {
        log.debug("REST request to get all FileUploads");
        return fileUploadRepository.findAll();
    }

    /**
     * {@code GET  /file-uploads/:id} : get the "id" fileUpload.
     *
     * @param id the id of the fileUpload to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileUpload, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/file-uploads/{id}")
    public ResponseEntity<FileUpload> getFileUpload(@PathVariable Long id) {
        log.debug("REST request to get FileUpload : {}", id);
        Optional<FileUpload> fileUpload = fileUploadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(fileUpload);
    }

    /**
     * {@code DELETE  /file-uploads/:id} : delete the "id" fileUpload.
     *
     * @param id the id of the fileUpload to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/file-uploads/{id}")
    public ResponseEntity<Void> deleteFileUpload(@PathVariable Long id) {
        log.debug("REST request to delete FileUpload : {}", id);
        fileUploadRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
