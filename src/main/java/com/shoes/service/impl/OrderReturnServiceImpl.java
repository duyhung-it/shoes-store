package com.shoes.service.impl;

import com.shoes.config.Constants;
import com.shoes.domain.Order;
import com.shoes.domain.OrderReturn;
import com.shoes.domain.OrderReturnDetails;
import com.shoes.domain.ReturnShoesDetails;
import com.shoes.repository.OrderReturnDetailsRepository;
import com.shoes.repository.OrderReturnRepository;
import com.shoes.repository.ReturnShoesDetailsRepository;
import com.shoes.service.OrderReturnService;
import com.shoes.service.dto.*;
import com.shoes.service.mapper.OrderReturnDetailsMapper;
import com.shoes.service.mapper.OrderReturnMapper;
import com.shoes.service.mapper.ReturnShoesDetailsMapper;
import com.shoes.util.DataUtils;
import com.shoes.util.SecurityUtils;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
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
                if (CollectionUtils.isNotEmpty(returnShoesDetails)) {
                    returnShoesDetailsRepository.saveAll(returnShoesDetails);
                }
            }
        });
        return orderReturnMapper.toDto(orderReturn);
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
