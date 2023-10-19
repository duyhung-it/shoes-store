package com.shoes.service;

import com.shoes.service.dto.SizeDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.shoes.domain.Size}.
 */
public interface SizeService {
    /**
     * Save a size.
     *
     * @param sizeDTO the entity to save.
     * @return the persisted entity.
     */
    SizeDTO save(SizeDTO sizeDTO);

    /**
     * Updates a size.
     *
     * @param sizeDTO the entity to update.
     * @return the persisted entity.
     */
    SizeDTO update(SizeDTO sizeDTO);

    /**
     * Partially updates a size.
     *
     * @param sizeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SizeDTO> partialUpdate(SizeDTO sizeDTO);

    /**
     * Get all the sizes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SizeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" size.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SizeDTO> findOne(Long id);

    /**
     * Delete the "id" size.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
