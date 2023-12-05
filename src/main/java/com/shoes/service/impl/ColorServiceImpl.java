package com.shoes.service.impl;

import com.shoes.domain.Color;
import com.shoes.repository.ColorRepository;
import com.shoes.service.ColorService;
import com.shoes.service.dto.ColorDTO;
import com.shoes.service.mapper.ColorMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Color}.
 */
@Service
@Transactional
public class ColorServiceImpl implements ColorService {

    private final Logger log = LoggerFactory.getLogger(ColorServiceImpl.class);

    private final ColorRepository colorRepository;

    private final ColorMapper colorMapper;

    public ColorServiceImpl(ColorRepository colorRepository, ColorMapper colorMapper) {
        this.colorRepository = colorRepository;
        this.colorMapper = colorMapper;
    }

    @Override
    public ColorDTO save(ColorDTO colorDTO) {
        log.debug("Request to save Color : {}", colorDTO);

        // Convert ColorDTO to Color entity
        Color color = colorMapper.toEntity(colorDTO);

        // Set the status to 1 by default
        color.setStatus(1);

        // Save the Color entity to the repository
        color = colorRepository.save(color);

        // Convert the saved Color entity back to ColorDTO
        return colorMapper.toDto(color);
    }


    @Override
    public ColorDTO update(ColorDTO colorDTO) {
        log.debug("Request to update Color : {}", colorDTO);

        // Convert ColorDTO to Color entity
        Color color = colorMapper.toEntity(colorDTO);

        // Set the status to 1 by default (nếu bạn muốn)
        color.setStatus(1);

        // Save the updated Color entity to the repository
        color = colorRepository.save(color);

        // Convert the updated Color entity back to ColorDTO
        return colorMapper.toDto(color);
    }


    @Override
    public Optional<ColorDTO> partialUpdate(ColorDTO colorDTO) {
        log.debug("Request to partially update Color : {}", colorDTO);

        return colorRepository
            .findById(colorDTO.getId())
            .map(existingColor -> {
                colorMapper.partialUpdate(existingColor, colorDTO);

                return existingColor;
            })
            .map(colorRepository::save)
            .map(colorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ColorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Colors with status = 1");
        // Retrieve all colors with status = 1 from the repository and map them to ColorDTO
        Page<Color> colorsWithStatus1 = colorRepository.findByStatus(1, pageable);
        return colorsWithStatus1.map(colorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ColorDTO> findDelete(Pageable pageable) {
        log.debug("Request to get all Colors with status = 1");
        // Retrieve all colors with status = 1 from the repository and map them to ColorDTO
        Page<Color> colorsWithStatus1 = colorRepository.findByStatus(0, pageable);
        return colorsWithStatus1.map(colorMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ColorDTO> findOne(Long id) {
        log.debug("Request to get Color : {}", id);
        return colorRepository.findById(id).map(colorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Color : {}", id);

        // Find the Color entity by ID
        Optional<Color> optionalColor = colorRepository.findById(id);

        // Check if the Color entity exists
        if (optionalColor.isPresent()) {
            // Update the status to 0 (inactive) instead of deleting
            Color color = optionalColor.get();
            color.setStatus(0);
            colorRepository.save(color);
        }
    }

}
