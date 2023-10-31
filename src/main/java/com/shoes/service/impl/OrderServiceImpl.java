package com.shoes.service.impl;

import com.shoes.config.Constants;
import com.shoes.domain.Order;
import com.shoes.domain.OrderDetails;
import com.shoes.domain.Payment;
import com.shoes.repository.OrderDetailsRepository;
import com.shoes.repository.OrderRepository;
import com.shoes.repository.PaymentRepository;
import com.shoes.service.OrderService;
import com.shoes.service.dto.OrderCreateDTO;
import com.shoes.service.dto.OrderDTO;
import com.shoes.service.dto.OrderDetailsDTO;
import com.shoes.service.dto.OrderResDTO;
import com.shoes.service.mapper.OrderDetailsMapper;
import com.shoes.service.mapper.OrderMapper;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Order}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final PaymentRepository paymentRepository;
    private final OrderDetailsMapper orderDetailsMapper;
    private final OrderDetailsRepository orderDetailsRepository;

    @Override
    public OrderDTO save(OrderCreateDTO orderDTO) {
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Request to save Order : {}", orderDTO);
        Order order = orderMapper.toOrderEntity(orderDTO);
        order.setStatus(Constants.STATUS.ACTIVE);
        if (Objects.isNull(order.getId())) {
            order.setCreatedBy(loggedUser);
        }
        order.setLastModifiedBy(loggedUser);
        Payment payment = new Payment();
        payment.setPaymentMethod(orderDTO.getPaymentMethod());
        payment.setCode(orderDTO.getCode() + Instant.EPOCH.getNano());
        payment.setPaymentStatus(orderDTO.getPaymentStatus() != null ? orderDTO.getPaymentStatus() : -1);
        paymentRepository.save(payment);
        order.setPayment(payment);
        order = orderRepository.save(order);
        List<OrderDetailsDTO> orderDetailsDTOList = orderDTO.getOrderDetailsDTOList();
        List<OrderDetails> orderDetailsList = orderDetailsMapper.toEntity(orderDetailsDTOList);
        for (OrderDetails orderDetails : orderDetailsList) {
            orderDetails.setOrder(order);
            orderDetails.setStatus(Constants.STATUS.ACTIVE);
        }
        orderDetailsRepository.saveAll(orderDetailsList);
        return orderMapper.toDto(order);
    }

    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        log.debug("Request to update Order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public Optional<OrderDTO> partialUpdate(OrderDTO orderDTO) {
        log.debug("Request to partially update Order : {}", orderDTO);

        return orderRepository
            .findById(orderDTO.getId())
            .map(existingOrder -> {
                orderMapper.partialUpdate(existingOrder, orderDTO);

                return existingOrder;
            })
            .map(orderRepository::save)
            .map(orderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Orders");
        return orderRepository.findAll(pageable).map(orderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResDTO findOne(Long id) {
        log.debug("Request to get Order : {}", id);
        Order order = orderRepository.findByIdAndStatus(id, Constants.STATUS.ACTIVE).orElse(null);
        if (Objects.nonNull(order)) {
            OrderResDTO orderResDTO = orderMapper.toOderResDTO(order);
            List<OrderDetails> orderDetailsList = orderDetailsRepository.findAllByOrder_IdAndStatus(id, Constants.STATUS.ACTIVE);
            orderResDTO.setOrderDetailsDTOList(orderDetailsMapper.toDto(orderDetailsList));
            return orderResDTO;
        }
        return new OrderResDTO();
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }

    @Override
    public Page<OrderDTO> getOrderByOwnerId(Long id, Pageable pageable) {
        return orderRepository.getOrderByOwnerId(id, pageable).map(orderMapper::toDto);
    }
}
