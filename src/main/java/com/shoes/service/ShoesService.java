package com.shoes.service;

import com.shoes.service.dto.ShoesDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.shoes.domain.Shoes}.
 */
public interface ShoesService {
    /**
     * Save a shoes.
     *
     * @param shoesDTO the entity to save.
     * @return the persisted entity.
     */
    ShoesDTO save(ShoesDTO shoesDTO);

    /**
     * Updates a shoes.
     *
     * @param shoesDTO the entity to update.
     * @return the persisted entity.
     */
    ShoesDTO update(ShoesDTO shoesDTO);

    /**
     * Partially updates a shoes.
     *
     * @param shoesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShoesDTO> partialUpdate(ShoesDTO shoesDTO);

    /**
     * Get all the shoes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    List<ShoesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shoes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShoesDTO> findOne(Long id);

    /**
     * Delete the "id" shoes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
