package com.shoes.service.impl;

import com.shoes.domain.ShoesDetails;
import com.shoes.repository.ShoesDetailsRepository;
import com.shoes.service.ShoesDetailsService;
import com.shoes.service.dto.ShoesDetailDTOCustom;
import com.shoes.service.dto.ShoesDetailsDTO;
import com.shoes.service.dto.ShopShoesDTO;
import com.shoes.service.mapper.ShoesDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShoesDetails}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ShoesDetailsServiceImpl implements ShoesDetailsService {

    private final Logger log = LoggerFactory.getLogger(ShoesDetailsServiceImpl.class);

    private final ShoesDetailsRepository shoesDetailsRepository;

    private final ShoesDetailsMapper shoesDetailsMapper;

    @Override
    public ShoesDetailsDTO save(ShoesDetailsDTO shoesDetailsDTO) {
        log.debug("Request to save ShoesDetails : {}", shoesDetailsDTO);
        ShoesDetails shoesDetails = shoesDetailsMapper.toEntity(shoesDetailsDTO);
        shoesDetails = shoesDetailsRepository.save(shoesDetails);
        return shoesDetailsMapper.toDto(shoesDetails);
    }

    @Override
    public ShoesDetailsDTO update(ShoesDetailsDTO shoesDetailsDTO) {
        log.debug("Request to update ShoesDetails : {}", shoesDetailsDTO);
        ShoesDetails shoesDetails = shoesDetailsMapper.toEntity(shoesDetailsDTO);
        shoesDetails = shoesDetailsRepository.save(shoesDetails);
        return shoesDetailsMapper.toDto(shoesDetails);
    }

    @Override
    public Optional<ShoesDetailsDTO> partialUpdate(ShoesDetailsDTO shoesDetailsDTO) {
        log.debug("Request to partially update ShoesDetails : {}", shoesDetailsDTO);

        return shoesDetailsRepository
            .findById(shoesDetailsDTO.getId())
            .map(existingShoesDetails -> {
                shoesDetailsMapper.partialUpdate(existingShoesDetails, shoesDetailsDTO);

                return existingShoesDetails;
            })
            .map(shoesDetailsRepository::save)
            .map(shoesDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShoesDetailsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShoesDetails");
        return shoesDetailsRepository.findAll().stream().map(shoesDetailsMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShoesDetailsDTO> findOne(Long id) {
        log.debug("Request to get ShoesDetails : {}", id);
        return shoesDetailsRepository.findById(id).map(shoesDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoesDetails : {}", id);
        shoesDetailsRepository.deleteById(id);
    }

    @Override
    public void deleteSoft(Long id) {
        log.debug("Request to delete ShoesDetails : {}", id);
        shoesDetailsRepository.softDeleteShoesDetailsById(id);
    }

    @Override
    public List<ShoesDetailDTOCustom> getNewShoesDetail() {
        return shoesDetailsRepository.getNewShoesDetail();
    }

    @Override
    public List<ShoesDetailDTOCustom> getNewDiscountShoesDetail() {
        return shoesDetailsRepository.getNewDiscountShoesDetail();
    }

    @Override
    public List<ShoesDetailDTOCustom> getBestSellerShoesDetail() {
        return shoesDetailsRepository.getBestSeller();
    }

    @Override
    public List<ShopShoesDTO> getDiscountShoes() {
        return shoesDetailsRepository.getShoesDiscount();
    }
}
