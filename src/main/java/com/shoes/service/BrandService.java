package com.shoes.service;

import com.shoes.service.dto.BrandDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.shoes.domain.Brand}.
 */
public interface BrandService {
    /**
     * Save a brand.
     *
     * @param brandDTO the entity to save.
     * @return the persisted entity.
     */
    BrandDTO save(BrandDTO brandDTO);

    /**
     * Updates a brand.
     *
     * @param brandDTO the entity to update.
     * @return the persisted entity.
     */
    BrandDTO update(BrandDTO brandDTO);

    /**
     * Partially updates a brand.
     *
     * @param brandDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BrandDTO> partialUpdate(BrandDTO brandDTO);

    /**
     * Get all the brands.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BrandDTO> findAll(Pageable pageable);

    /**
     * Get the "id" brand.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BrandDTO> findOne(Long id);

    /**
     * Delete the "id" brand.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for brands by their code and/or name, with optional pagination.
     *
     * @param code     The code to search for (optional). If null or empty, code will not be used as a search criterion.
     * @param name     The name to search for (optional). If null or empty, name will not be used as a search criterion.
     * @param pageable The pagination information to control the result size and page number.
     * @return A page of {@link BrandDTO} objects matching the search criteria, wrapped in a {@link Page}.
     */
    Page<BrandDTO> findByCodeAndName(String code, String name, Pageable pageable);
    public Page<BrandDTO> findAllWithStatus1(Pageable pageable);
}
