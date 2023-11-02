package com.shoes.service;

import com.shoes.service.dto.FileUploadDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.shoes.domain.FileUpload}.
 */
public interface FileUploadService {
    /**
     * Save a fileUpload.
     *
     * @param fileUploadDTO the entity to save.
     * @return the persisted entity.
     */
    FileUploadDTO save(FileUploadDTO fileUploadDTO);

    /**
     * Updates a fileUpload.
     *
     * @param fileUploadDTO the entity to update.
     * @return the persisted entity.
     */
    FileUploadDTO update(FileUploadDTO fileUploadDTO);

    /**
     * Partially updates a fileUpload.
     *
     * @param fileUploadDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FileUploadDTO> partialUpdate(FileUploadDTO fileUploadDTO);

    /**
     * Get all the fileUploads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FileUploadDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fileUpload.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileUploadDTO> findOne(Long id);

    Optional<FileUploadDTO> findOneByPath(String path);

    /**
     * Delete the "id" fileUpload.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
