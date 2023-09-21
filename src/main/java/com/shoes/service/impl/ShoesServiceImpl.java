package com.shoes.service.impl;

import com.shoes.domain.Shoes;
import com.shoes.repository.ShoesRepository;
import com.shoes.service.ShoesService;
import com.shoes.service.dto.ShoesDTO;
import com.shoes.service.mapper.ShoesMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Shoes}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ShoesServiceImpl implements ShoesService {

    private final Logger log = LoggerFactory.getLogger(ShoesServiceImpl.class);

    private final ShoesRepository shoesRepository;

    private final ShoesMapper shoesMapper;

    @Override
    public ShoesDTO save(ShoesDTO shoesDTO) {
        log.debug("Request to save Shoes : {}", shoesDTO);
        Shoes shoes = shoesMapper.toEntity(shoesDTO);
        shoes = shoesRepository.save(shoes);
        return shoesMapper.toDto(shoes);
    }

    @Override
    public ShoesDTO update(ShoesDTO shoesDTO) {
        log.debug("Request to update Shoes : {}", shoesDTO);
        Shoes shoes = shoesMapper.toEntity(shoesDTO);
        shoes = shoesRepository.save(shoes);
        return shoesMapper.toDto(shoes);
    }

    @Override
    public Optional<ShoesDTO> partialUpdate(ShoesDTO shoesDTO) {
        log.debug("Request to partially update Shoes : {}", shoesDTO);

        return shoesRepository
            .findById(shoesDTO.getId())
            .map(existingShoes -> {
                shoesMapper.partialUpdate(existingShoes, shoesDTO);

                return existingShoes;
            })
            .map(shoesRepository::save)
            .map(shoesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShoesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Shoes");
        return shoesRepository.findAll(pageable).map(shoesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShoesDTO> findOne(Long id) {
        log.debug("Request to get Shoes : {}", id);
        return shoesRepository.findById(id).map(shoesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Shoes : {}", id);
        shoesRepository.deleteById(id);
    }
}
