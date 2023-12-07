package com.shoes.service.impl;

import com.shoes.domain.Size;
import com.shoes.repository.SizeRepository;
import com.shoes.service.SizeService;
import com.shoes.service.dto.SizeDTO;
import com.shoes.service.mapper.SizeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Size}.
 */
@Service
@Transactional
public class SizeServiceImpl implements SizeService {

    private final Logger log = LoggerFactory.getLogger(SizeServiceImpl.class);

    private final SizeRepository sizeRepository;

    private final SizeMapper sizeMapper;

    public SizeServiceImpl(SizeRepository sizeRepository, SizeMapper sizeMapper) {
        this.sizeRepository = sizeRepository;
        this.sizeMapper = sizeMapper;
    }

    @Override
    public SizeDTO save(SizeDTO sizeDTO) {
        log.debug("Request to save Size : {}", sizeDTO);

        // Convert SizeDTO to Size entity
        Size size = sizeMapper.toEntity(sizeDTO);

        // Set the status to the desired value, for example 1 for active
        size.setStatus(1);

        // Save the Size entity to the repository
        size = sizeRepository.save(size);

        // Convert the saved Size entity back to SizeDTO
        return sizeMapper.toDto(size);
    }

    @Override
    public SizeDTO update(SizeDTO sizeDTO) {
        log.debug("Request to update Size : {}", sizeDTO);

        // Ensure status remains 1
        sizeDTO.setStatus(1);

        Size size = sizeMapper.toEntity(sizeDTO);
        size = sizeRepository.save(size);
        return sizeMapper.toDto(size);
    }

    @Override
    public Optional<SizeDTO> partialUpdate(SizeDTO sizeDTO) {
        log.debug("Request to partially update Size : {}", sizeDTO);

        return sizeRepository
            .findById(sizeDTO.getId())
            .map(existingSize -> {
                sizeMapper.partialUpdate(existingSize, sizeDTO);

                return existingSize;
            })
            .map(sizeRepository::save)
            .map(sizeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SizeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Sizes with status = 1");

        // Retrieve all sizes with status = 1 from the repository and map them to SizeDTO
        Page<Size> sizesWithStatus1 = sizeRepository.findByStatus(1, pageable);
        return sizesWithStatus1.map(sizeMapper::toDto);
    }

    @Override
    public Page<SizeDTO> findDelete(Pageable pageable) {
        log.debug("Request to get all Sizes with status = 0");

        // Retrieve all sizes with status = 0 from the repository and map them to SizeDTO
        Page<Size> sizesWithStatus1 = sizeRepository.findByStatus(0, pageable);
        return sizesWithStatus1.map(sizeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SizeDTO> findOne(Long id) {
        log.debug("Request to get Size : {}", id);
        return sizeRepository.findById(id).map(sizeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Size : {}", id);

        // Attempt to find the existing Size entity by ID
        sizeRepository
            .findById(id)
            .ifPresent(existingSize -> {
                // Set the status to 0 (inactive) instead of deleting the record
                existingSize.setStatus(0);
                sizeRepository.save(existingSize);
            });
    }
}
