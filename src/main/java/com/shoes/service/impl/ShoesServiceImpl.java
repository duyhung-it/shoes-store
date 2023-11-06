package com.shoes.service.impl;

import com.shoes.config.Constants;
import com.shoes.domain.Shoes;
import com.shoes.domain.ShoesDetails;
import com.shoes.repository.ShoesDetailsRepository;
import com.shoes.repository.ShoesRepository;
import com.shoes.repository.SizeRepository;
import com.shoes.service.ShoesService;
import com.shoes.service.dto.*;
import com.shoes.service.mapper.ShoesDetailsMapper;
import com.shoes.service.mapper.ShoesMapper;
import com.shoes.service.mapper.SizeMapper;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.SendTo;
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
    private final ShoesDetailsRepository shoesDetailsRepository;
    private final ShoesDetailsMapper shoesDetailsMapper;
    private final SizeRepository sizeRepository;
    private final SizeMapper sizeMapper;
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
    public List<ShoesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Shoes");
        List<ShoesDTO> shoesDTOList = shoesRepository
            .findAllByStatus(Constants.STATUS.ACTIVE)
            .stream()
            .map(shoesMapper::toDto)
            .collect(Collectors.toList());
        List<Long> ids = shoesDTOList.stream().map(ShoesDTO::getId).collect(Collectors.toList());
        Map<Long, List<ShoesDetailsDTO>> map = shoesDetailsRepository
            .findAllByShoes_IdInAndStatus(ids, Constants.STATUS.ACTIVE)
            .stream()
            .map(shoesDetailsMapper::toDto)
            .collect(Collectors.groupingBy(shoesDetailsDTO -> shoesDetailsDTO.getShoes().getId()));
        Map<Long, List<ShoesDetailsCustomeDTO>> map1 = shoesDetailsRepository
            .findShoesDetailsGroupByColor(ids, Constants.STATUS.ACTIVE)
            .stream()
            .map(shoesDetailsMapper::toShoesCustomDTO)
            .collect(Collectors.groupingBy(shoesDetailsDTO -> shoesDetailsDTO.getShoes().getId()));
        shoesDTOList.forEach(shoesDTO -> {
            List<ShoesDetailsDTO> shoesDetailsDTOS = map.get(shoesDTO.getId());
            if (CollectionUtils.isNotEmpty(shoesDetailsDTOS)) {
                Set<SizeDTO> sizeDTOS = shoesDetailsDTOS.stream().map(ShoesDetailsDTO::getSize).collect(Collectors.toSet());
                Set<ColorDTO> colorDTOS = shoesDetailsDTOS.stream().map(ShoesDetailsDTO::getColor).collect(Collectors.toSet());
                shoesDTO.setSizeDTOS(sizeDTOS);
                shoesDTO.setColorDTOS(colorDTOS);
            }
            shoesDTO.setShoesDetails(shoesDetailsDTOS);
            List<ShoesDetailsCustomeDTO> list = map1.get(shoesDTO.getId());
            if (Objects.nonNull(list)) {
                list.forEach(shoesDetailsCustomeDTO -> {
                    List<SizeDTO> list1 = sizeRepository
                        .findByShoesIdAndColor(
                            shoesDetailsCustomeDTO.getShoes().getId(),
                            shoesDetailsCustomeDTO.getColor().getId(),
                            Constants.STATUS.ACTIVE
                        )
                        .stream()
                        .map(sizeMapper::toDto)
                        .collect(Collectors.toList());
                    shoesDetailsCustomeDTO.setSizes(list1);
                });
            }
            shoesDTO.setShoesDetailsCustomeDTOS(list);
        });
        return shoesDTOList;
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
