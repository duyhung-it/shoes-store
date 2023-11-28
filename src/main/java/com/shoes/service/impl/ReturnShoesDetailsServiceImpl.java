package com.shoes.service.impl;

import com.shoes.domain.ReturnShoesDetails;
import com.shoes.repository.ReturnShoesDetailsRepository;
import com.shoes.service.ReturnShoesDetailsService;
import com.shoes.service.dto.ReturnShoesDetailsDTO;
import com.shoes.service.mapper.ReturnShoesDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReturnShoesDetails}.
 */
@Service
@Transactional
public class ReturnShoesDetailsServiceImpl implements ReturnShoesDetailsService {

    private final Logger log = LoggerFactory.getLogger(ReturnShoesDetailsServiceImpl.class);

    private final ReturnShoesDetailsRepository returnShoesDetailsRepository;

    private final ReturnShoesDetailsMapper returnShoesDetailsMapper;

    public ReturnShoesDetailsServiceImpl(
        ReturnShoesDetailsRepository returnShoesDetailsRepository,
        ReturnShoesDetailsMapper returnShoesDetailsMapper
    ) {
        this.returnShoesDetailsRepository = returnShoesDetailsRepository;
        this.returnShoesDetailsMapper = returnShoesDetailsMapper;
    }

    @Override
    public ReturnShoesDetailsDTO save(ReturnShoesDetailsDTO returnShoesDetailsDTO) {
        log.debug("Request to save ReturnShoesDetails : {}", returnShoesDetailsDTO);
        ReturnShoesDetails returnShoesDetails = returnShoesDetailsMapper.toEntity(returnShoesDetailsDTO);
        returnShoesDetails = returnShoesDetailsRepository.save(returnShoesDetails);
        return returnShoesDetailsMapper.toDto(returnShoesDetails);
    }

    @Override
    public ReturnShoesDetailsDTO update(ReturnShoesDetailsDTO returnShoesDetailsDTO) {
        log.debug("Request to update ReturnShoesDetails : {}", returnShoesDetailsDTO);
        ReturnShoesDetails returnShoesDetails = returnShoesDetailsMapper.toEntity(returnShoesDetailsDTO);
        returnShoesDetails = returnShoesDetailsRepository.save(returnShoesDetails);
        return returnShoesDetailsMapper.toDto(returnShoesDetails);
    }

    @Override
    public Optional<ReturnShoesDetailsDTO> partialUpdate(ReturnShoesDetailsDTO returnShoesDetailsDTO) {
        log.debug("Request to partially update ReturnShoesDetails : {}", returnShoesDetailsDTO);

        return returnShoesDetailsRepository
            .findById(returnShoesDetailsDTO.getId())
            .map(existingReturnShoesDetails -> {
                returnShoesDetailsMapper.partialUpdate(existingReturnShoesDetails, returnShoesDetailsDTO);

                return existingReturnShoesDetails;
            })
            .map(returnShoesDetailsRepository::save)
            .map(returnShoesDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReturnShoesDetailsDTO> findAll() {
        log.debug("Request to get all ReturnShoesDetails");
        return returnShoesDetailsRepository
            .findAll()
            .stream()
            .map(returnShoesDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReturnShoesDetailsDTO> findOne(Long id) {
        log.debug("Request to get ReturnShoesDetails : {}", id);
        return returnShoesDetailsRepository.findById(id).map(returnShoesDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReturnShoesDetails : {}", id);
        returnShoesDetailsRepository.deleteById(id);
    }
}
