package com.shoes.service.impl;

import com.shoes.domain.ShoesCategory;
import com.shoes.repository.ShoesCategoryRepository;
import com.shoes.service.ShoesCategoryService;
import com.shoes.service.dto.ShoesCategoryDTO;
import com.shoes.service.mapper.ShoesCategoryMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShoesCategory}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ShoesCategoryServiceImpl implements ShoesCategoryService {

    private final Logger log = LoggerFactory.getLogger(ShoesCategoryServiceImpl.class);

    private final ShoesCategoryRepository shoesCategoryRepository;

    private final ShoesCategoryMapper shoesCategoryMapper;

    @Override
    public ShoesCategoryDTO save(ShoesCategoryDTO shoesCategoryDTO) {
        log.debug("Request to save ShoesCategory : {}", shoesCategoryDTO);
        ShoesCategory shoesCategory = shoesCategoryMapper.toEntity(shoesCategoryDTO);
        shoesCategory = shoesCategoryRepository.save(shoesCategory);
        return shoesCategoryMapper.toDto(shoesCategory);
    }

    @Override
    public ShoesCategoryDTO update(ShoesCategoryDTO shoesCategoryDTO) {
        log.debug("Request to update ShoesCategory : {}", shoesCategoryDTO);
        ShoesCategory shoesCategory = shoesCategoryMapper.toEntity(shoesCategoryDTO);
        shoesCategory = shoesCategoryRepository.save(shoesCategory);
        return shoesCategoryMapper.toDto(shoesCategory);
    }

    @Override
    public Optional<ShoesCategoryDTO> partialUpdate(ShoesCategoryDTO shoesCategoryDTO) {
        log.debug("Request to partially update ShoesCategory : {}", shoesCategoryDTO);

        return shoesCategoryRepository
            .findById(shoesCategoryDTO.getId())
            .map(existingShoesCategory -> {
                shoesCategoryMapper.partialUpdate(existingShoesCategory, shoesCategoryDTO);

                return existingShoesCategory;
            })
            .map(shoesCategoryRepository::save)
            .map(shoesCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShoesCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShoesCategories");
        return shoesCategoryRepository.findAll(pageable).map(shoesCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShoesCategoryDTO> findOne(Long id) {
        log.debug("Request to get ShoesCategory : {}", id);
        return shoesCategoryRepository.findById(id).map(shoesCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoesCategory : {}", id);
        shoesCategoryRepository.deleteById(id);
    }
}
