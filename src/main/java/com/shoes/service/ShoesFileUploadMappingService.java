package com.shoes.service;

import com.shoes.service.dto.ShoesFileUploadMappingDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.shoes.domain.ShoesFileUploadMapping}.
 */
public interface ShoesFileUploadMappingService {
    /**
     * Save a shoesFileUploadMapping.
     *
     * @param shoesFileUploadMappingDTO the entity to save.
     * @return the persisted entity.
     */
    ShoesFileUploadMappingDTO save(ShoesFileUploadMappingDTO shoesFileUploadMappingDTO);

    /**
     * Updates a shoesFileUploadMapping.
     *
     * @param shoesFileUploadMappingDTO the entity to update.
     * @return the persisted entity.
     */
    ShoesFileUploadMappingDTO update(ShoesFileUploadMappingDTO shoesFileUploadMappingDTO);

    /**
     * Partially updates a shoesFileUploadMapping.
     *
     * @param shoesFileUploadMappingDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShoesFileUploadMappingDTO> partialUpdate(ShoesFileUploadMappingDTO shoesFileUploadMappingDTO);

    /**
     * Get all the shoesFileUploadMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShoesFileUploadMappingDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shoesFileUploadMapping.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShoesFileUploadMappingDTO> findOne(Long id);

    /**
     * Delete the "id" shoesFileUploadMapping.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
