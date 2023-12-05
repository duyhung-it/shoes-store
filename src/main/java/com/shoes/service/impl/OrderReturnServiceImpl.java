package com.shoes.service.impl;

import com.shoes.config.Constants;
import com.shoes.domain.*;
import com.shoes.repository.OrderReturnDetailsRepository;
import com.shoes.repository.OrderReturnRepository;
import com.shoes.repository.ReturnShoesDetailsRepository;
import com.shoes.repository.ShoesDetailsRepository;
import com.shoes.service.OrderReturnService;
import com.shoes.service.dto.*;
import com.shoes.service.mapper.OrderReturnDetailsMapper;
import com.shoes.service.mapper.OrderReturnMapper;
import com.shoes.service.mapper.ReturnShoesDetailsMapper;
import com.shoes.util.DataUtils;
import com.shoes.util.SecurityUtils;
import com.shoes.util.Translator;
import com.shoes.web.rest.errors.BadRequestAlertException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderReturn}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class OrderReturnServiceImpl implements OrderReturnService {

    private final Logger log = LoggerFactory.getLogger(OrderReturnServiceImpl.class);

    private final OrderReturnRepository orderReturnRepository;
    private final OrderReturnDetailsRepository orderReturnDetailsRepository;
    private final OrderReturnDetailsMapper orderReturnDetailsMapper;

    private final OrderReturnMapper orderReturnMapper;
    private final ReturnShoesDetailsMapper returnShoesDetailsMapper;
    private final ReturnShoesDetailsRepository returnShoesDetailsRepository;
    private final ShoesDetailsRepository shoesDetailsRepository;

    @Override
    public OrderReturnDTO save(OrderReturnReqDTO orderReturnDTO) {
        log.debug("Request to save OrderReturn : {}", orderReturnDTO);
        String loggedUser = SecurityUtils.getCurrentUserLogin().get();
        OrderReturn orderReturn = new OrderReturn();
        orderReturn.setOrder(new Order(orderReturnDTO.getOrderId()));
        orderReturn.setCode(generateCode());
        orderReturn.setStatus(Constants.ORDER_RETURN.PENDING);
        orderReturn.setCreatedBy(loggedUser);
        orderReturn.setCreatedDate(DataUtils.getCurrentDateTime());
        orderReturn = orderReturnRepository.save(orderReturn);
        List<OrderReturnDetailsDTO> orderReturnDTOList = orderReturnDTO.getReturnOrderDetails();
        OrderReturn finalOrderReturn = orderReturn;
        orderReturnDTOList.forEach(orderReturnDetailsDTO -> {
            orderReturnDetailsDTO.setOrderDetails(new OrderDetailsDTO(orderReturnDetailsDTO.getOrderDetailsId()));
            orderReturnDetailsDTO.setOrderReturn(orderReturnMapper.toDto(finalOrderReturn));
            OrderReturnDetails orderReturnDetails = orderReturnDetailsMapper.toEntity(orderReturnDetailsDTO);
            orderReturnDetails.setStatus(Constants.STATUS.ACTIVE);
            orderReturnDetails.setCreatedBy(loggedUser);
            orderReturnDetails.setCreatedDate(DataUtils.getCurrentDateTime());
            orderReturnDetailsRepository.save(orderReturnDetails);
            if (Objects.equals(orderReturnDetails.getType(), 1)) {
                List<ReturnShoesDetailsDTO> returnShoesDetailsDTOList = orderReturnDetailsDTO.getReturnShoesDetails();
                for (ReturnShoesDetailsDTO returnShoesDetailsDTO : returnShoesDetailsDTOList) {
                    returnShoesDetailsDTO.setOrderReturnDetailsDTO(new OrderReturnDetailsDTO(orderReturnDetails.getId()));
                    returnShoesDetailsDTO.setShoesDetails(new ShoesDetailsDTO(returnShoesDetailsDTO.getShoesDetailsId()));
                    returnShoesDetailsDTO.setStatus(Constants.STATUS.ACTIVE);
                    returnShoesDetailsDTO.setCreatedBy(loggedUser);
                    returnShoesDetailsDTO.setCreatedDate(DataUtils.getCurrentDateTime());
                }
                List<ReturnShoesDetails> returnShoesDetails = returnShoesDetailsMapper.toEntity(returnShoesDetailsDTOList);
                returnShoesDetails.forEach(returnShoesDetails1 -> {
                    returnShoesDetails1.setOrderReturnDetails(orderReturnDetails);
                });
                if (CollectionUtils.isNotEmpty(returnShoesDetails)) {
                    returnShoesDetailsRepository.saveAll(returnShoesDetails);
                }
            }
        });
        return orderReturnMapper.toDto(orderReturn);
    }

    @Override
    public OrderReturnDTO verify(VerifyOrderReturnDTO verifyOrderReturnDTO) {
        String loggedUser = SecurityUtils.getCurrentUserLogin().get();
        OrderReturn orderReturn = orderReturnRepository.findById(verifyOrderReturnDTO.getId()).orElse(null);
        if (!DataUtils.isNull(orderReturn) && Constants.ORDER_RETURN.PENDING.equals(orderReturn.getStatus())) {
            List<OrderReturnDetails> orderReturnDetails = orderReturnDetailsRepository.findAllByOrderReturn_IdAndStatus(
                verifyOrderReturnDTO.getId(),
                Constants.STATUS.ACTIVE
            );
            List<ShoesDetails> shoesDetailsList = new ArrayList<>();
            for (OrderReturnDetails orderReturnDetails1 : orderReturnDetails) {
                OrderReturnDetailsDTO orderReturnDetailsDTO = verifyOrderReturnDTO
                    .getList()
                    .stream()
                    .filter(orderReturnDetailsDTO1 -> Objects.equals(orderReturnDetailsDTO1.getId(), orderReturnDetails1.getId()))
                    .findAny()
                    .get();
                orderReturnDetails1.setErrorQuantity(orderReturnDetailsDTO.getErrorQuantity());
                ShoesDetails shoesDetails = shoesDetailsRepository.findByIdAndStatus(
                    orderReturnDetails1.getOrderDetails().getShoesDetails().getId(),
                    Constants.STATUS.ACTIVE
                );
                if (!DataUtils.isNull(orderReturnDetails1.getErrorQuantity())) {
                    shoesDetails.setQuantity(
                        shoesDetails.getQuantity() + orderReturnDetails1.getReturnQuantity() - orderReturnDetails1.getErrorQuantity()
                    );
                } else {
                    shoesDetails.setQuantity(shoesDetails.getQuantity() + orderReturnDetails1.getReturnQuantity());
                }
                shoesDetails.setLastModifiedBy(loggedUser);
                shoesDetails.setLastModifiedDate(DataUtils.getCurrentDateTime());
                shoesDetailsList.add(shoesDetails);
                if (orderReturnDetails1.getType().equals(1)) {
                    List<ReturnShoesDetails> returnShoesDetails = returnShoesDetailsRepository.findAllByOrderReturnDetails_IdAndStatus(
                        orderReturnDetails1.getId(),
                        Constants.STATUS.ACTIVE
                    );
                    List<ShoesDetails> shoesDetailsList1 = returnShoesDetails
                        .stream()
                        .map(orderDetails -> {
                            ShoesDetails shoesDetails1 = orderDetails.getShoesDetails();
                            if (ObjectUtils.compare(shoesDetails1.getQuantity(), (long) orderDetails.getQuantity()) < 0) {
                                throw new BadRequestAlertException(
                                    Translator.toLocal(
                                        "Số lượng không đủ:" +
                                        shoesDetails1.getShoes().getName() +
                                        "-" +
                                        shoesDetails1.getColor().getName() +
                                        "-" +
                                        shoesDetails1.getBrand().getName()
                                    ),
                                    "ENTITY_NAME",
                                    "not_exist"
                                );
                            }
                            shoesDetails1.setQuantity(shoesDetails1.getQuantity() - orderDetails.getQuantity());
                            return shoesDetails1;
                        })
                        .collect(Collectors.toList());
                    shoesDetailsList.addAll(shoesDetailsList1);
                }
            }
            shoesDetailsRepository.saveAll(shoesDetailsList);
            orderReturn.setStatus(Constants.ORDER_RETURN.PROCESSING);
            orderReturn.setLastModifiedBy(loggedUser);
            orderReturn.setLastModifiedDate(DataUtils.getCurrentDateTime());
            orderReturnRepository.save(orderReturn);
            return orderReturnMapper.toDto(orderReturn);
        }
        return null;
    }

    @Override
    public OrderReturnDTO cancel(Long id) {
        String loggedUser = SecurityUtils.getCurrentUserLogin().get();
        OrderReturn orderReturn = orderReturnRepository.findById(id).orElse(null);
        if (!DataUtils.isNull(orderReturn) && Constants.ORDER_RETURN.PENDING.equals(orderReturn.getStatus())) {
            orderReturn.setStatus(Constants.ORDER_RETURN.CANCEL);
            orderReturn.setLastModifiedBy(loggedUser);
            orderReturn.setLastModifiedDate(DataUtils.getCurrentDateTime());
            orderReturnRepository.save(orderReturn);
            return orderReturnMapper.toDto(orderReturn);
        }
        return null;
    }

    @Override
    public OrderReturnDTO finish(Long id) {
        String loggedUser = SecurityUtils.getCurrentUserLogin().get();
        OrderReturn orderReturn = orderReturnRepository.findById(id).orElse(null);
        if (!DataUtils.isNull(orderReturn) && Constants.ORDER_RETURN.PROCESSING.equals(orderReturn.getStatus())) {
            orderReturn.setStatus(Constants.ORDER_RETURN.FINISH);
            orderReturn.setLastModifiedBy(loggedUser);
            orderReturn.setLastModifiedDate(DataUtils.getCurrentDateTime());
            orderReturnRepository.save(orderReturn);
            return orderReturnMapper.toDto(orderReturn);
        }
        return null;
    }

    public String generateCode() {
        Instant currentDateTime = DataUtils.getCurrentDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = formatter.format(LocalDate.ofInstant(currentDateTime, ZoneId.of("UTC")));
        String dateString = DataUtils.makeLikeStr(formattedDate);
        List<OrderReturn> list = orderReturnRepository.findByCreatedDateLike(dateString);
        int numberInDay = list.size() + 1;
        String code = DataUtils.replaceSpecialCharacters(formattedDate);
        String baseCode = "RO";
        return baseCode + code + numberInDay;
    }

    @Override
    public OrderReturnDTO update(OrderReturnDTO orderReturnDTO) {
        log.debug("Request to update OrderReturn : {}", orderReturnDTO);
        OrderReturn orderReturn = orderReturnMapper.toEntity(orderReturnDTO);
        orderReturn = orderReturnRepository.save(orderReturn);
        return orderReturnMapper.toDto(orderReturn);
    }

    @Override
    public List<OrderReturnSearchResDTO> search(OrderSearchReqDTO searchReqDTO) {
        return orderReturnRepository.search(searchReqDTO);
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
    public OrderReturnDTO findOne(Long id) {
        log.debug("Request to get OrderReturn : {}", id);
        OrderReturnDTO orderReturn = orderReturnRepository.findById(id).map(orderReturnMapper::toDto).get();
        List<OrderReturnDetailsDTO> orderReturns = orderReturnDetailsMapper.toDto(
            orderReturnDetailsRepository.findAllByOrderReturn_IdAndStatus(id, Constants.STATUS.ACTIVE)
        );
        for (OrderReturnDetailsDTO orderReturnDetailsDTO : orderReturns) {
            List<ReturnShoesDetailsDTO> returnShoesDetailsDTOS = returnShoesDetailsMapper.toDto(
                returnShoesDetailsRepository.findAllByOrderReturnDetails_IdAndStatus(orderReturnDetailsDTO.getId(), Constants.STATUS.ACTIVE)
            );
            orderReturnDetailsDTO.setReturnShoesDetails(returnShoesDetailsDTOS);
        }
        orderReturn.setOrderReturnDetailsDTOS(orderReturns);
        return orderReturn;
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderReturn : {}", id);
        orderReturnRepository.deleteById(id);
    }
}
