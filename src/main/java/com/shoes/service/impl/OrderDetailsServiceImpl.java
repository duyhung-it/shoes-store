package com.shoes.service.impl;

import com.shoes.domain.OrderDetails;
import com.shoes.repository.OrderDetailsRepository;
import com.shoes.service.OrderDetailsService;
import com.shoes.service.dto.CartDetailsDTO;
import com.shoes.service.dto.OrderDetailDTOInterface;
import com.shoes.service.dto.OrderDetailsDTO;
import com.shoes.service.mapper.OrderDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderDetails}.
 */
@Service
@Transactional
public class OrderDetailsServiceImpl implements OrderDetailsService {

    private final Logger log = LoggerFactory.getLogger(OrderDetailsServiceImpl.class);

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderDetailsMapper orderDetailsMapper;

    public OrderDetailsServiceImpl(OrderDetailsRepository orderDetailsRepository, OrderDetailsMapper orderDetailsMapper) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderDetailsMapper = orderDetailsMapper;
    }

    @Override
    public OrderDetailsDTO save(OrderDetailsDTO orderDetailsDTO) {
        log.debug("Request to save OrderDetails : {}", orderDetailsDTO);
        OrderDetails orderDetails = orderDetailsMapper.toEntity(orderDetailsDTO);
        orderDetails = orderDetailsRepository.save(orderDetails);
        return orderDetailsMapper.toDto(orderDetails);
    }

    @Override
    public OrderDetailsDTO update(OrderDetailsDTO orderDetailsDTO) {
        log.debug("Request to update OrderDetails : {}", orderDetailsDTO);
        OrderDetails orderDetails = orderDetailsMapper.toEntity(orderDetailsDTO);
        orderDetails = orderDetailsRepository.save(orderDetails);
        return orderDetailsMapper.toDto(orderDetails);
    }

    @Override
    public Optional<OrderDetailsDTO> partialUpdate(OrderDetailsDTO orderDetailsDTO) {
        log.debug("Request to partially update OrderDetails : {}", orderDetailsDTO);

        return orderDetailsRepository
            .findById(orderDetailsDTO.getId())
            .map(existingOrderDetails -> {
                orderDetailsMapper.partialUpdate(existingOrderDetails, orderDetailsDTO);

                return existingOrderDetails;
            })
            .map(orderDetailsRepository::save)
            .map(orderDetailsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetailsDTO> findAll() {
        log.debug("Request to get all OrderDetails");
        return orderDetailsRepository.findAll().stream().map(orderDetailsMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDetailsDTO> findOne(Long id) {
        log.debug("Request to get OrderDetails : {}", id);
        return orderDetailsRepository.findById(id).map(orderDetailsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderDetails : {}", id);
        orderDetailsRepository.deleteById(id);
    }

    @Override
    public List<OrderDetailDTOInterface> findAllByOrder_Id(Long id) {
        return orderDetailsRepository.findAllByOrder_Id(id);
    }
}
