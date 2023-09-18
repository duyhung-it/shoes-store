package com.shoes.service.impl;

import com.shoes.domain.ShoesCategoryValue;
import com.shoes.repository.ShoesCategoryRepository;
import com.shoes.repository.ShoesCategoryValueRepository;
import com.shoes.service.ShoesCategoryValueService;
import com.shoes.service.dto.ShoesCategoryValueDTO;
import com.shoes.service.mapper.ShoesCategoryValueMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShoesCategoryValue}.
 */
@Service
@Transactional
public class ShoesCategoryValueServiceImpl implements ShoesCategoryValueService {

    private final Logger log = LoggerFactory.getLogger(ShoesCategoryValueServiceImpl.class);

    private final ShoesCategoryValueRepository shoesCategoryValueRepository;

    private final ShoesCategoryValueMapper shoesCategoryValueMapper;

    private final ShoesCategoryRepository shoesCategoryRepository;

    public ShoesCategoryValueServiceImpl(
        ShoesCategoryValueRepository shoesCategoryValueRepository,
        ShoesCategoryValueMapper shoesCategoryValueMapper,
        ShoesCategoryRepository shoesCategoryRepository
    ) {
        this.shoesCategoryValueRepository = shoesCategoryValueRepository;
        this.shoesCategoryValueMapper = shoesCategoryValueMapper;
        this.shoesCategoryRepository = shoesCategoryRepository;
    }

    @Override
    public ShoesCategoryValueDTO save(ShoesCategoryValueDTO shoesCategoryValueDTO) {
        log.debug("Request to save ShoesCategoryValue : {}", shoesCategoryValueDTO);
        ShoesCategoryValue shoesCategoryValue = shoesCategoryValueMapper.toEntity(shoesCategoryValueDTO);
        Long shoesCategoryId = shoesCategoryValueDTO.getCategory().getId();
        shoesCategoryRepository.findById(shoesCategoryId).ifPresent(shoesCategoryValue::category);
        shoesCategoryValue = shoesCategoryValueRepository.save(shoesCategoryValue);
        return shoesCategoryValueMapper.toDto(shoesCategoryValue);
    }

    @Override
    public ShoesCategoryValueDTO update(ShoesCategoryValueDTO shoesCategoryValueDTO) {
        log.debug("Request to update ShoesCategoryValue : {}", shoesCategoryValueDTO);
        ShoesCategoryValue shoesCategoryValue = shoesCategoryValueMapper.toEntity(shoesCategoryValueDTO);
        Long shoesCategoryId = shoesCategoryValueDTO.getCategory().getId();
        shoesCategoryRepository.findById(shoesCategoryId).ifPresent(shoesCategoryValue::category);
        shoesCategoryValue = shoesCategoryValueRepository.save(shoesCategoryValue);
        return shoesCategoryValueMapper.toDto(shoesCategoryValue);
    }

    @Override
    public Optional<ShoesCategoryValueDTO> partialUpdate(ShoesCategoryValueDTO shoesCategoryValueDTO) {
        log.debug("Request to partially update ShoesCategoryValue : {}", shoesCategoryValueDTO);

        return shoesCategoryValueRepository
            .findById(shoesCategoryValueDTO.getId())
            .map(existingShoesCategoryValue -> {
                shoesCategoryValueMapper.partialUpdate(existingShoesCategoryValue, shoesCategoryValueDTO);

                return existingShoesCategoryValue;
            })
            .map(shoesCategoryValueRepository::save)
            .map(shoesCategoryValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShoesCategoryValueDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShoesCategoryValues");
        return shoesCategoryValueRepository.findAll(pageable).map(shoesCategoryValueMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShoesCategoryValueDTO> findOne(Long id) {
        log.debug("Request to get ShoesCategoryValue : {}", id);
        return shoesCategoryValueRepository.findById(id).map(shoesCategoryValueMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoesCategoryValue : {}", id);
        shoesCategoryValueRepository.deleteById(id);
    }
}
