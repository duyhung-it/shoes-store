package com.shoes.service.impl;

import com.shoes.domain.ShoesCategoryValueMapping;
import com.shoes.repository.ShoesCategoryValueMappingRepository;
import com.shoes.service.ShoesCategoryValueMappingService;
import com.shoes.service.dto.ShoesCategoryValueMappingDTO;
import com.shoes.service.mapper.ShoesCategoryValueMappingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShoesCategoryValueMapping}.
 */
@Service
@Transactional
public class ShoesCategoryValueMappingServiceImpl implements ShoesCategoryValueMappingService {

    private final Logger log = LoggerFactory.getLogger(ShoesCategoryValueMappingServiceImpl.class);

    private final ShoesCategoryValueMappingRepository shoesCategoryValueMappingRepository;

    private final ShoesCategoryValueMappingMapper shoesCategoryValueMappingMapper;

    public ShoesCategoryValueMappingServiceImpl(
        ShoesCategoryValueMappingRepository shoesCategoryValueMappingRepository,
        ShoesCategoryValueMappingMapper shoesCategoryValueMappingMapper
    ) {
        this.shoesCategoryValueMappingRepository = shoesCategoryValueMappingRepository;
        this.shoesCategoryValueMappingMapper = shoesCategoryValueMappingMapper;
    }

    @Override
    public ShoesCategoryValueMappingDTO save(ShoesCategoryValueMappingDTO shoesCategoryValueMappingDTO) {
        log.debug("Request to save ShoesCategoryValueMapping : {}", shoesCategoryValueMappingDTO);
        ShoesCategoryValueMapping shoesCategoryValueMapping = shoesCategoryValueMappingMapper.toEntity(shoesCategoryValueMappingDTO);
        shoesCategoryValueMapping = shoesCategoryValueMappingRepository.save(shoesCategoryValueMapping);
        return shoesCategoryValueMappingMapper.toDto(shoesCategoryValueMapping);
    }

    @Override
    public ShoesCategoryValueMappingDTO update(ShoesCategoryValueMappingDTO shoesCategoryValueMappingDTO) {
        log.debug("Request to update ShoesCategoryValueMapping : {}", shoesCategoryValueMappingDTO);
        ShoesCategoryValueMapping shoesCategoryValueMapping = shoesCategoryValueMappingMapper.toEntity(shoesCategoryValueMappingDTO);
        shoesCategoryValueMapping = shoesCategoryValueMappingRepository.save(shoesCategoryValueMapping);
        return shoesCategoryValueMappingMapper.toDto(shoesCategoryValueMapping);
    }

    @Override
    public Optional<ShoesCategoryValueMappingDTO> partialUpdate(ShoesCategoryValueMappingDTO shoesCategoryValueMappingDTO) {
        log.debug("Request to partially update ShoesCategoryValueMapping : {}", shoesCategoryValueMappingDTO);

        return shoesCategoryValueMappingRepository
            .findById(shoesCategoryValueMappingDTO.getId())
            .map(existingShoesCategoryValueMapping -> {
                shoesCategoryValueMappingMapper.partialUpdate(existingShoesCategoryValueMapping, shoesCategoryValueMappingDTO);

                return existingShoesCategoryValueMapping;
            })
            .map(shoesCategoryValueMappingRepository::save)
            .map(shoesCategoryValueMappingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShoesCategoryValueMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShoesCategoryValueMappings");
        return shoesCategoryValueMappingRepository.findAll(pageable).map(shoesCategoryValueMappingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShoesCategoryValueMappingDTO> findOne(Long id) {
        log.debug("Request to get ShoesCategoryValueMapping : {}", id);
        return shoesCategoryValueMappingRepository.findById(id).map(shoesCategoryValueMappingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoesCategoryValueMapping : {}", id);
        shoesCategoryValueMappingRepository.deleteById(id);
    }
}
