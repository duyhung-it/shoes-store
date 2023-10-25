package com.shoes.service.impl;

import com.shoes.domain.DiscountShoesDetails;
import com.shoes.repository.DiscountShoesDetailsRepository;
import com.shoes.service.DiscountShoesDetailsService;
import com.shoes.service.dto.DiscountShoesDetailsDTO;
import com.shoes.service.mapper.DiscountShoesDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DiscountShoesDetails}.
 */
@Service
@Transactional
public class DiscountShoesDetailsServiceImpl implements DiscountShoesDetailsService {

    private final Logger log = LoggerFactory.getLogger(DiscountShoesDetailsServiceImpl.class);

    private final DiscountShoesDetailsRepository discountShoesDetailsRepository;

    private final DiscountShoesDetailsMapper discountShoesDetailsMapper;

    public DiscountShoesDetailsServiceImpl(
        DiscountShoesDetailsRepository discountShoesDetailsRepository,
        DiscountShoesDetailsMapper discountShoesDetailsMapper
    ) {
        this.discountShoesDetailsRepository = discountShoesDetailsRepository;
        this.discountShoesDetailsMapper = discountShoesDetailsMapper;
    }

    @Override
    public DiscountShoesDetailsDTO save(DiscountShoesDetailsDTO discountShoesDetailsDTO) {
        log.debug("Request to save DiscountShoesDetails : {}", discountShoesDetailsDTO);
        DiscountShoesDetails discountShoesDetails = discountShoesDetailsMapper.toEntity(discountShoesDetailsDTO);
        discountShoesDetails = discountShoesDetailsRepository.save(discountShoesDetails);
        return discountShoesDetailsMapper.toDto(discountShoesDetails);
    }

    @Override
    public DiscountShoesDetailsDTO update(DiscountShoesDetailsDTO discountShoesDetailsDTO) {
        log.debug("Request to update DiscountShoesDetails : {}", discountShoesDetailsDTO);
        DiscountShoesDetails discountShoesDetails = discountShoesDetailsMapper.toEntity(discountShoesDetailsDTO);
        discountShoesDetails = discountShoesDetailsRepository.save(discountShoesDetails);
        return discountShoesDetailsMapper.toDto(discountShoesDetails);
    }

    @Override
    public Optional<DiscountShoesDetailsDTO> partialUpdate(DiscountShoesDetailsDTO discountShoesDetailsDTO) {
        log.debug("Request to partially update DiscountShoesDetails : {}", discountShoesDetailsDTO);

        return discountShoesDetailsRepository
            .findById(discountShoesDetailsDTO.getId())
            .map(existingDiscountShoesDetails -> {
                discountShoesDetailsMapper.partialUpdate(existingDiscountShoesDetails, discountShoesDetailsDTO);

                return existingDiscountShoesDetails;
            })
            .map(discountShoesDetailsRepository::save)
            .map(discountShoesDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscountShoesDetailsDTO> findAll() {
        log.debug("Request to get all DiscountShoesDetails");
        return discountShoesDetailsRepository
            .findAll()
            .stream()
            .map(discountShoesDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DiscountShoesDetailsDTO> findOne(Long id) {
        log.debug("Request to get DiscountShoesDetails : {}", id);
        return discountShoesDetailsRepository.findById(id).map(discountShoesDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DiscountShoesDetails : {}", id);
        discountShoesDetailsRepository.deleteById(id);
    }
}
