package com.shoes.service.impl;

import com.shoes.config.Constants;
import com.shoes.domain.*;
import com.shoes.repository.*;
import com.shoes.service.OrderService;
import com.shoes.service.dto.*;
import com.shoes.service.mapper.AddressMapper;
import com.shoes.service.mapper.OrderDetailsMapper;
import com.shoes.service.mapper.OrderMapper;
import com.shoes.util.DataUtils;
import com.shoes.util.Translator;
import com.shoes.web.rest.errors.BadRequestAlertException;
import io.undertow.util.DateUtils;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
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
    private static final String ENTITY_NAME = "order";
    private final PaymentRepository paymentRepository;
    private final OrderDetailsMapper orderDetailsMapper;
    private final OrderDetailsRepository orderDetailsRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final String baseCode = "HD";
    private final ShoesDetailsRepository shoesDetailsRepository;

    @Override
    public OrderDTO save(OrderCreateDTO orderDTO) {
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Request to save Order : {}", orderDTO);

        Order order = orderMapper.toOrderEntity(orderDTO);
        Address address = addressMapper.toEntity(orderDTO.getUserAddress());
        address.setStatus(Constants.STATUS.ACTIVE);
        address.setCreatedBy(loggedUser);
        address.setLastModifiedBy(loggedUser);
        order.setUserAddress(addressRepository.save(address));
        if (Objects.isNull(order.getId())) {
            order.setCreatedBy(loggedUser);
            order.setStatus(Constants.ORDER_STATUS.PENDING_CHECKOUT);
            order.setCode(this.generateCode());
        }
        order.setLastModifiedBy(loggedUser);

        Payment payment = new Payment();
        payment.setPaymentMethod(orderDTO.getPaymentMethod());
        payment.setCode(orderDTO.getCode() + Instant.EPOCH.getNano());
        payment.setPaymentStatus(orderDTO.getPaymentStatus() != null ? orderDTO.getPaymentStatus() : -1);
        payment.setStatus(Constants.STATUS.ACTIVE);
        payment.setLastModifiedBy(loggedUser);
        payment.setCreatedBy(loggedUser);
        paymentRepository.save(payment);
        order.setPayment(payment);
        order = orderRepository.save(order);
        List<OrderDetailsDTO> orderDetailsDTOList = orderDTO.getOrderDetailsDTOList();
        orderDetailsDTOList.forEach(orderDetailsDTO -> {
            ShoesDetails shoesDetails = shoesDetailsRepository.findByIdAndStatus(
                orderDetailsDTO.getShoesDetails().getId(),
                Constants.STATUS.ACTIVE
            );
            if (ObjectUtils.isEmpty(shoesDetails)) {
                throw new BadRequestAlertException("Giày không tồn tại", ENTITY_NAME, "not_exist");
            }
            if (ObjectUtils.compare(shoesDetails.getQuantity(), (long) orderDetailsDTO.getQuantity()) < 0) {
                throw new BadRequestAlertException("Số lượng không đủ", ENTITY_NAME, "not_exist");
            }
        });
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
        Order order = orderRepository.findById(id).orElse(null);
        if (Objects.nonNull(order)) {
            OrderResDTO orderResDTO = orderMapper.toOderResDTO(order);
            List<OrderDetails> orderDetailsList = orderDetailsRepository.findAllByOrder_IdAndStatus(id, Constants.STATUS.ACTIVE);
            orderResDTO.setOrderDetailsDTOList(orderDetailsMapper.toDto(orderDetailsList));
            return orderResDTO;
        }
        return new OrderResDTO();
    }

    public String generateCode() {
        Instant currentDateTime = DataUtils.getCurrentDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = formatter.format(LocalDate.ofInstant(currentDateTime, ZoneId.of("UTC")));
        String dateString = DataUtils.makeLikeStr(formattedDate);
        List<Order> list = orderRepository.findByCreatedDate(dateString);
        int numberInDay = list.size() + 1;
        String code = DataUtils.replaceSpecialCharacters(formattedDate);
        return baseCode + code + numberInDay;
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

    @Override
    public List<OrderSearchResDTO> search(OrderSearchReqDTO searchReqDTO) {
        return orderRepository.search(searchReqDTO);
    }

    @Override
    public OrderDTO updateStatus(Long idOrder) {
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Order order = this.validateUpdateStatus(idOrder);
        if (!Constants.ORDER_STATUS.SUCCESS.equals(order.getStatus()) && !Constants.ORDER_STATUS.CANCELED.equals(order.getStatus())) {
            if (Constants.ORDER_STATUS.PENDING_CHECKOUT.equals(order.getStatus())) {
                order.setStatus(order.getStatus() - 1);
            } else {
                order.setStatus(order.getStatus() + 1);
            }
            order.setLastModifiedBy(loggedUser);
            order.setLastModifiedDate(Instant.now().plus(7, ChronoUnit.HOURS));
            orderRepository.save(order);
        }
        return orderMapper.toDto(order);
    }

    @Override
    public Map<Integer, Integer> getQuantityPerOrderStatus() {
        Map<Integer, Integer> mapQuantity =
            this.orderRepository.getQuantityOrders()
                .stream()
                .collect(Collectors.toMap(OrderStatusDTO::getStatus, OrderStatusDTO::getQuantity));
        return mapQuantity;
    }

    @Override
    public void verifyOrder(List<Long> orderId) {
        List<Order> orders = orderRepository.findAllByIdInAndStatus(orderId, Constants.ORDER_STATUS.PENDING);
        if (!Objects.equals(orderId.size(), orders.size())) {
            throw new BadRequestAlertException(Translator.toLocal("Hóa đơn không tồn tại"), ENTITY_NAME, "not_exist");
        }
        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAllByOrder_IdInAndStatus(orderId, Constants.STATUS.ACTIVE);
        for (Order order : orders) {
            order.setStatus(Constants.ORDER_STATUS.WAIT_DELIVERY);
        }
        orderRepository.saveAll(orders);
        List<ShoesDetails> shoesDetails = orderDetailsList
            .stream()
            .map(orderDetails -> {
                ShoesDetails shoesDetails1 = orderDetails.getShoesDetails();
                if (ObjectUtils.compare(shoesDetails1.getQuantity(), (long) orderDetails.getQuantity()) < 0) {
                    throw new BadRequestAlertException(Translator.toLocal("Số lượng không đủ"), ENTITY_NAME, "not_exist");
                }
                shoesDetails1.setQuantity(shoesDetails1.getQuantity() - orderDetails.getQuantity());
                return shoesDetails1;
            })
            .collect(Collectors.toList());
        shoesDetailsRepository.saveAll(shoesDetails);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order =
            this.orderRepository.findById(orderId)
                .orElseThrow(() -> new BadRequestAlertException(Translator.toLocal("error.order.not.exist"), "Order", "not_exist"));
        if (Constants.ORDER_STATUS.PENDING.equals(order.getStatus()) || Constants.ORDER_STATUS.PENDING_CHECKOUT.equals(order.getStatus())) {
            order.setStatus(Constants.ORDER_STATUS.CANCELED);
            orderRepository.save(order);
        }
    }

    private Order validateUpdateStatus(Long idOrder) {
        Order order =
            this.orderRepository.findById(idOrder)
                .orElseThrow(() -> new BadRequestAlertException(Translator.toLocal("error.order.not.exist"), "Order", "not_exist"));
        return order;
    }
}
