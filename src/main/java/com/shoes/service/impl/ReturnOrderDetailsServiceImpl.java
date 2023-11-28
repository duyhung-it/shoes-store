package com.shoes.service.impl;

import com.shoes.domain.ReturnOrderDetails;
import com.shoes.repository.ReturnOrderDetailsRepository;
import com.shoes.service.ReturnOrderDetailsService;
import com.shoes.service.dto.ReturnOrderDetailsDTO;
import com.shoes.service.mapper.ReturnOrderDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ReturnOrderDetails}.
 */
@Service
@Transactional
public class ReturnOrderDetailsServiceImpl implements ReturnOrderDetailsService {

    private final Logger log = LoggerFactory.getLogger(ReturnOrderDetailsServiceImpl.class);

    private final ReturnOrderDetailsRepository returnOrderDetailsRepository;

    private final ReturnOrderDetailsMapper returnOrderDetailsMapper;

    public ReturnOrderDetailsServiceImpl(
        ReturnOrderDetailsRepository returnOrderDetailsRepository,
        ReturnOrderDetailsMapper returnOrderDetailsMapper
    ) {
        this.returnOrderDetailsRepository = returnOrderDetailsRepository;
        this.returnOrderDetailsMapper = returnOrderDetailsMapper;
    }

    @Override
    public ReturnOrderDetailsDTO save(ReturnOrderDetailsDTO returnOrderDetailsDTO) {
        log.debug("Request to save ReturnOrderDetails : {}", returnOrderDetailsDTO);
        ReturnOrderDetails returnOrderDetails = returnOrderDetailsMapper.toEntity(returnOrderDetailsDTO);
        returnOrderDetails = returnOrderDetailsRepository.save(returnOrderDetails);
        return returnOrderDetailsMapper.toDto(returnOrderDetails);
    }

    @Override
    public ReturnOrderDetailsDTO update(ReturnOrderDetailsDTO returnOrderDetailsDTO) {
        log.debug("Request to update ReturnOrderDetails : {}", returnOrderDetailsDTO);
        ReturnOrderDetails returnOrderDetails = returnOrderDetailsMapper.toEntity(returnOrderDetailsDTO);
        // no save call needed as we have no fields that can be updated
        return returnOrderDetailsMapper.toDto(returnOrderDetails);
    }

    @Override
    public Optional<ReturnOrderDetailsDTO> partialUpdate(ReturnOrderDetailsDTO returnOrderDetailsDTO) {
        log.debug("Request to partially update ReturnOrderDetails : {}", returnOrderDetailsDTO);

        return returnOrderDetailsRepository
            .findById(returnOrderDetailsDTO.getId())
            .map(existingReturnOrderDetails -> {
                returnOrderDetailsMapper.partialUpdate(existingReturnOrderDetails, returnOrderDetailsDTO);

                return existingReturnOrderDetails;
            })
            // .map(returnOrderDetailsRepository::save)
            .map(returnOrderDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReturnOrderDetailsDTO> findAll() {
        log.debug("Request to get all ReturnOrderDetails");
        return returnOrderDetailsRepository
            .findAll()
            .stream()
            .map(returnOrderDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReturnOrderDetailsDTO> findOne(Long id) {
        log.debug("Request to get ReturnOrderDetails : {}", id);
        return returnOrderDetailsRepository.findById(id).map(returnOrderDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ReturnOrderDetails : {}", id);
        returnOrderDetailsRepository.deleteById(id);
    }
}
