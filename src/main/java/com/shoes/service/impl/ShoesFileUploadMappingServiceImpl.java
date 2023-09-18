package com.shoes.service.impl;

import com.shoes.domain.ShoesFileUploadMapping;
import com.shoes.repository.ShoesFileUploadMappingRepository;
import com.shoes.service.ShoesFileUploadMappingService;
import com.shoes.service.dto.ShoesFileUploadMappingDTO;
import com.shoes.service.mapper.ShoesFileUploadMappingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShoesFileUploadMapping}.
 */
@Service
@Transactional
public class ShoesFileUploadMappingServiceImpl implements ShoesFileUploadMappingService {

    private final Logger log = LoggerFactory.getLogger(ShoesFileUploadMappingServiceImpl.class);

    private final ShoesFileUploadMappingRepository shoesFileUploadMappingRepository;

    private final ShoesFileUploadMappingMapper shoesFileUploadMappingMapper;

    public ShoesFileUploadMappingServiceImpl(
        ShoesFileUploadMappingRepository shoesFileUploadMappingRepository,
        ShoesFileUploadMappingMapper shoesFileUploadMappingMapper
    ) {
        this.shoesFileUploadMappingRepository = shoesFileUploadMappingRepository;
        this.shoesFileUploadMappingMapper = shoesFileUploadMappingMapper;
    }

    @Override
    public ShoesFileUploadMappingDTO save(ShoesFileUploadMappingDTO shoesFileUploadMappingDTO) {
        log.debug("Request to save ShoesFileUploadMapping : {}", shoesFileUploadMappingDTO);
        ShoesFileUploadMapping shoesFileUploadMapping = shoesFileUploadMappingMapper.toEntity(shoesFileUploadMappingDTO);
        shoesFileUploadMapping = shoesFileUploadMappingRepository.save(shoesFileUploadMapping);
        return shoesFileUploadMappingMapper.toDto(shoesFileUploadMapping);
    }

    @Override
    public ShoesFileUploadMappingDTO update(ShoesFileUploadMappingDTO shoesFileUploadMappingDTO) {
        log.debug("Request to update ShoesFileUploadMapping : {}", shoesFileUploadMappingDTO);
        ShoesFileUploadMapping shoesFileUploadMapping = shoesFileUploadMappingMapper.toEntity(shoesFileUploadMappingDTO);
        shoesFileUploadMapping = shoesFileUploadMappingRepository.save(shoesFileUploadMapping);
        return shoesFileUploadMappingMapper.toDto(shoesFileUploadMapping);
    }

    @Override
    public Optional<ShoesFileUploadMappingDTO> partialUpdate(ShoesFileUploadMappingDTO shoesFileUploadMappingDTO) {
        log.debug("Request to partially update ShoesFileUploadMapping : {}", shoesFileUploadMappingDTO);

        return shoesFileUploadMappingRepository
            .findById(shoesFileUploadMappingDTO.getId())
            .map(existingShoesFileUploadMapping -> {
                shoesFileUploadMappingMapper.partialUpdate(existingShoesFileUploadMapping, shoesFileUploadMappingDTO);

                return existingShoesFileUploadMapping;
            })
            .map(shoesFileUploadMappingRepository::save)
            .map(shoesFileUploadMappingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShoesFileUploadMappingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShoesFileUploadMappings");
        return shoesFileUploadMappingRepository.findAll(pageable).map(shoesFileUploadMappingMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShoesFileUploadMappingDTO> findOne(Long id) {
        log.debug("Request to get ShoesFileUploadMapping : {}", id);
        return shoesFileUploadMappingRepository.findById(id).map(shoesFileUploadMappingMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoesFileUploadMapping : {}", id);
        shoesFileUploadMappingRepository.deleteById(id);
    }
}
