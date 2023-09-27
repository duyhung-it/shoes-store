package com.shoes.service;

import com.shoes.service.dto.ShoesCategoryDTO;
import com.shoes.service.dto.ShoesCategorySearchReqDTO;
import com.shoes.service.dto.ShoesCategorySearchResDTO;
import com.shoes.service.dto.ShoesCategoryUpdateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.shoes.domain.ShoesCategory}.
 */
public interface ShoesCategoryService {
    /**
     * Save a shoesCategory.
     *
     * @param shoesCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    ShoesCategoryDTO save(ShoesCategoryDTO shoesCategoryDTO);

    /**
     * Updates a shoesCategory.
     *
     * @param shoesCategoryDTO the entity to update.
     * @return the persisted entity.
     */
    ShoesCategoryDTO update(ShoesCategoryUpdateDTO shoesCategoryDTO, Long idShoesCategory);

    /**
     * Partially updates a shoesCategory.
     *
     * @param shoesCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShoesCategoryDTO> partialUpdate(ShoesCategoryDTO shoesCategoryDTO);

    /**
     * Get all the shoesCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ShoesCategorySearchResDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shoesCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    ShoesCategoryDTO findOne(Long id);

    /**
     * Delete the "id" shoesCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<ShoesCategorySearchResDTO> search(ShoesCategorySearchReqDTO searchText, Pageable pageable);
}
