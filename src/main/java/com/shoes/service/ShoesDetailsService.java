package com.shoes.service;

import com.shoes.service.dto.ShoesDetailDTOCustom;
import com.shoes.service.dto.ShoesDetailsDTO;
import com.shoes.service.dto.ShopShoesDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.shoes.domain.ShoesDetails}.
 */
public interface ShoesDetailsService {
    /**
     * Save a shoesDetails.
     *
     * @param shoesDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    ShoesDetailsDTO save(ShoesDetailsDTO shoesDetailsDTO);

    /**
     * Updates a shoesDetails.
     *
     * @param shoesDetailsDTO the entity to update.
     * @return the persisted entity.
     */
    ShoesDetailsDTO update(ShoesDetailsDTO shoesDetailsDTO);

    /**
     * Partially updates a shoesDetails.
     *
     * @param shoesDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShoesDetailsDTO> partialUpdate(ShoesDetailsDTO shoesDetailsDTO);

    /**
     * Get all the shoesDetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    List<ShoesDetailsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shoesDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShoesDetailsDTO> findOne(Long id);

    /**
     * Delete the "id" shoesDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    void deleteSoft(Long id);

    List<ShoesDetailDTOCustom> getNewShoesDetail();
    List<ShoesDetailDTOCustom> getNewDiscountShoesDetail();
    List<ShopShoesDTO> getDiscountShoes();
}
