package com.shoes.service;

import com.shoes.service.dto.FeedBackDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.shoes.domain.FeedBack}.
 */
public interface FeedBackService {
    /**
     * Save a feedBack.
     *
     * @param feedBackDTO the entity to save.
     * @return the persisted entity.
     */
    FeedBackDTO save(FeedBackDTO feedBackDTO);

    /**
     * Updates a feedBack.
     *
     * @param feedBackDTO the entity to update.
     * @return the persisted entity.
     */
    FeedBackDTO update(FeedBackDTO feedBackDTO);

    /**
     * Partially updates a feedBack.
     *
     * @param feedBackDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FeedBackDTO> partialUpdate(FeedBackDTO feedBackDTO);

    /**
     * Get all the feedBacks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FeedBackDTO> findAll(Pageable pageable);

    /**
     * Get the "id" feedBack.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FeedBackDTO> findOne(Long id);

    /**
     * Delete the "id" feedBack.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
