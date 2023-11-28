package com.shoes.service.impl;

import com.shoes.domain.OrderReturnDetails;
import com.shoes.repository.OrderReturnDetailsRepository;
import com.shoes.service.OrderReturnDetailsService;
import com.shoes.service.dto.OrderReturnDetailsDTO;
import com.shoes.service.mapper.OrderReturnDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderReturnDetails}.
 */
@Service
@Transactional
public class OrderReturnDetailsServiceImpl implements OrderReturnDetailsService {

    private final Logger log = LoggerFactory.getLogger(OrderReturnDetailsServiceImpl.class);

    private final OrderReturnDetailsRepository orderReturnDetailsRepository;

    private final OrderReturnDetailsMapper orderReturnDetailsMapper;

    public OrderReturnDetailsServiceImpl(
        OrderReturnDetailsRepository orderReturnDetailsRepository,
        OrderReturnDetailsMapper orderReturnDetailsMapper
    ) {
        this.orderReturnDetailsRepository = orderReturnDetailsRepository;
        this.orderReturnDetailsMapper = orderReturnDetailsMapper;
    }

    @Override
    public OrderReturnDetailsDTO save(OrderReturnDetailsDTO orderReturnDetailsDTO) {
        log.debug("Request to save OrderReturnDetails : {}", orderReturnDetailsDTO);
        OrderReturnDetails orderReturnDetails = orderReturnDetailsMapper.toEntity(orderReturnDetailsDTO);
        orderReturnDetails = orderReturnDetailsRepository.save(orderReturnDetails);
        return orderReturnDetailsMapper.toDto(orderReturnDetails);
    }

    @Override
    public OrderReturnDetailsDTO update(OrderReturnDetailsDTO orderReturnDetailsDTO) {
        log.debug("Request to update OrderReturnDetails : {}", orderReturnDetailsDTO);
        OrderReturnDetails orderReturnDetails = orderReturnDetailsMapper.toEntity(orderReturnDetailsDTO);
        orderReturnDetails = orderReturnDetailsRepository.save(orderReturnDetails);
        return orderReturnDetailsMapper.toDto(orderReturnDetails);
    }

    @Override
    public Optional<OrderReturnDetailsDTO> partialUpdate(OrderReturnDetailsDTO orderReturnDetailsDTO) {
        log.debug("Request to partially update OrderReturnDetails : {}", orderReturnDetailsDTO);

        return orderReturnDetailsRepository
            .findById(orderReturnDetailsDTO.getId())
            .map(existingOrderReturnDetails -> {
                orderReturnDetailsMapper.partialUpdate(existingOrderReturnDetails, orderReturnDetailsDTO);

                return existingOrderReturnDetails;
            })
            .map(orderReturnDetailsRepository::save)
            .map(orderReturnDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderReturnDetailsDTO> findAll() {
        log.debug("Request to get all OrderReturnDetails");
        return orderReturnDetailsRepository
            .findAll()
            .stream()
            .map(orderReturnDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderReturnDetailsDTO> findOne(Long id) {
        log.debug("Request to get OrderReturnDetails : {}", id);
        return orderReturnDetailsRepository.findById(id).map(orderReturnDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderReturnDetails : {}", id);
        orderReturnDetailsRepository.deleteById(id);
    }
}
