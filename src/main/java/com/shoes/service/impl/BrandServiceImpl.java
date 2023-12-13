package com.shoes.service.impl;

import com.shoes.domain.Brand;
import com.shoes.repository.BrandRepository;
import com.shoes.service.BrandService;
import com.shoes.service.dto.BrandDTO;
import com.shoes.service.mapper.BrandMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Brand}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);

    private final BrandRepository brandRepository;

    private final BrandMapper brandMapper;

    /**
     * Save a brand with status = 1 by default.
     *
     * @param brandDTO The entity to save.
     * @return The saved {@link BrandDTO}.
     */
    @Override
    public BrandDTO save(BrandDTO brandDTO) {
        log.debug("Request to save Brand : {}", brandDTO);

        // Convert BrandDTO to Brand entity
        Brand brand = brandMapper.toEntity(brandDTO);

        // Set the status to 1 by default
        brand.setStatus(1);

        // Save the Brand entity to the repository
        brand = brandRepository.save(brand);

        // Convert the saved Brand entity back to BrandDTO
        return brandMapper.toDto(brand);
    }

    /**
     * Update an existing brand.
     *
     * @param brandDTO The {@link BrandDTO} to update.
     * @return The updated {@link BrandDTO}.
     */
    @Override
    public BrandDTO update(BrandDTO brandDTO) {
        log.debug("Request to update Brand : {}", brandDTO);

        // Convert BrandDTO to Brand entity
        Brand brand = brandMapper.toEntity(brandDTO);

        brand.setStatus(1);

        // Save the updated Brand entity to the repository
        brand = brandRepository.save(brand);

        // Convert the updated Brand entity back to BrandDTO
        return brandMapper.toDto(brand);
    }

    /**
     * Partially update an existing brand.
     *
     * @param brandDTO The {@link BrandDTO} containing the fields to update.
     * @return An {@link Optional} of the updated {@link BrandDTO}, or an empty {@link Optional} if the brand with the given ID was not found.
     */
    @Override
    public Optional<BrandDTO> partialUpdate(BrandDTO brandDTO) {
        log.debug("Request to partially update Brand : {}", brandDTO);

        // Attempt to find the existing Brand entity by ID
        return brandRepository
            .findById(brandDTO.getId())
            .map(existingBrand -> {
                // Perform partial update of the existing Brand entity with fields from brandDTO
                brandMapper.partialUpdate(existingBrand, brandDTO);

                return existingBrand;
            })
            .map(brandRepository::save) // Save the updated Brand entity
            .map(brandMapper::toDto); // Convert the updated Brand entity back to BrandDTO and return it
    }

    /**
     * Retrieve all brands with optional pagination.
     *
     * @param pageable The pagination information to control the result size and page number.
     * @return A page of {@link BrandDTO} objects representing all brands, wrapped in a {@link Page}.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BrandDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Brands");

        // Retrieve all brands from the repository and map them to BrandDTO
        return brandRepository.findAll(pageable).map(brandMapper::toDto);
    }

    @Override
    public Page<BrandDTO> findDelete(Pageable pageable) {
        log.debug("Request to get all Brands with status = 0");

        // Retrieve all brands with status = 0 from the repository and map them to BrandDTO
        Page<Brand> brandsWithStatus1 = brandRepository.findByStatus(0, pageable);
        return brandsWithStatus1.map(brandMapper::toDto);
    }

    /**
     * Retrieve a single brand by its ID.
     *
     * @param id The ID of the brand to retrieve.
     * @return An {@link Optional} of the {@link BrandDTO} representing the brand with the given ID, or an empty {@link Optional} if no brand was found.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BrandDTO> findOne(Long id) {
        log.debug("Request to get Brand : {}", id);

        // Attempt to find a brand by its ID in the repository and map it to BrandDTO
        return brandRepository.findById(id).map(brandMapper::toDto);
    }

    /**
     * Mark a brand as inactive (status = 0) instead of deleting it.
     *
     * @param id The ID of the brand to mark as inactive.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to mark Brand as inactive: {}", id);

        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            brand.setStatus(0); // Cập nhật trường status thành 0
            brandRepository.save(brand);
        }
    }

    /**
     * Search for brands by their code and/or name with optional pagination.
     *
     * @param code     The code to search for (optional). If null or empty, code will not be used as a search criterion.
     * @param name     The name to search for (optional). If null or empty, name will not be used as a search criterion.
     * @param pageable The pagination information to control the result size and page number.
     * @return A page of {@link BrandDTO} objects representing the brands that match the search criteria, wrapped in a {@link Page}.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BrandDTO> findByCodeAndName(String code, String name, Pageable pageable) {
        log.debug("Request to find Brands by code and name: {} {}", code, name);

        Page<Brand> resultPage;

        if (!StringUtils.isEmpty(code) && !StringUtils.isEmpty(name)) {
            // Search based on both `code` and `name`
            resultPage = brandRepository.findByNameContainingAndCodeContaining(name, code, pageable);
        } else if (!StringUtils.isEmpty(code)) {
            // Search based on `code`
            resultPage = brandRepository.findByCodeContaining(code, pageable);
        } else if (!StringUtils.isEmpty(name)) {
            // Search based on `name`
            resultPage = brandRepository.findByNameContaining(name, pageable);
        } else {
            // No search criteria provided, return an empty page
            resultPage = Page.empty(pageable);
        }

        // Map the results to a Page<BrandDTO>
        return resultPage.map(brandMapper::toDto);
    }

    /**
     * Retrieve all brands with status = 1 and optional pagination.
     *
     * @param pageable The pagination information to control the result size and page number.
     * @return A page of {@link BrandDTO} objects representing all brands with status = 1, wrapped in a {@link Page}.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BrandDTO> findAllWithStatus1(Pageable pageable) {
        log.debug("Request to get all Brands with status = 1");

        // Retrieve all brands with status = 1 from the repository and map them to BrandDTO
        Page<Brand> brandsWithStatus1 = brandRepository.findByStatus(1, pageable);
        return brandsWithStatus1.map(brandMapper::toDto);
    }
}
