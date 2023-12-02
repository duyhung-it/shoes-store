package com.shoes.service.impl;

import com.shoes.domain.OrderReturn;
import com.shoes.repository.OrderReturnRepository;
import com.shoes.service.OrderReturnService;
import com.shoes.service.dto.OrderReturnDTO;
import com.shoes.service.mapper.OrderReturnMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderReturn}.
 */
@Service
@Transactional
public class OrderReturnServiceImpl implements OrderReturnService {

    private final Logger log = LoggerFactory.getLogger(OrderReturnServiceImpl.class);

    private final OrderReturnRepository orderReturnRepository;

    private final OrderReturnMapper orderReturnMapper;

    public OrderReturnServiceImpl(OrderReturnRepository orderReturnRepository, OrderReturnMapper orderReturnMapper) {
        this.orderReturnRepository = orderReturnRepository;
        this.orderReturnMapper = orderReturnMapper;
    }

    @Override
    public OrderReturnDTO save(OrderReturnDTO orderReturnDTO) {
        log.debug("Request to save OrderReturn : {}", orderReturnDTO);
        OrderReturn orderReturn = orderReturnMapper.toEntity(orderReturnDTO);
        orderReturn = orderReturnRepository.save(orderReturn);
        return orderReturnMapper.toDto(orderReturn);
    }

    @Override
    public OrderReturnDTO update(OrderReturnDTO orderReturnDTO) {
        log.debug("Request to update OrderReturn : {}", orderReturnDTO);
        OrderReturn orderReturn = orderReturnMapper.toEntity(orderReturnDTO);
        orderReturn = orderReturnRepository.save(orderReturn);
        return orderReturnMapper.toDto(orderReturn);
    }

    @Override
    public Optional<OrderReturnDTO> partialUpdate(OrderReturnDTO orderReturnDTO) {
        log.debug("Request to partially update OrderReturn : {}", orderReturnDTO);

        return orderReturnRepository
            .findById(orderReturnDTO.getId())
            .map(existingOrderReturn -> {
                orderReturnMapper.partialUpdate(existingOrderReturn, orderReturnDTO);

                return existingOrderReturn;
            })
            .map(orderReturnRepository::save)
            .map(orderReturnMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderReturnDTO> findAll() {
        log.debug("Request to get all OrderReturns");
        return orderReturnRepository.findAll().stream().map(orderReturnMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderReturnDTO> findOne(Long id) {
        log.debug("Request to get OrderReturn : {}", id);
        return orderReturnRepository.findById(id).map(orderReturnMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderReturn : {}", id);
        orderReturnRepository.deleteById(id);
    }
}
