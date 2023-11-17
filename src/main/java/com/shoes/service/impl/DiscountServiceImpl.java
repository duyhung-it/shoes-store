package com.shoes.service.impl;

import com.shoes.config.Constants;
import com.shoes.domain.Discount;
import com.shoes.domain.DiscountShoesDetails;
import com.shoes.repository.DiscountRepository;
import com.shoes.repository.DiscountShoesDetailsRepository;
import com.shoes.service.DiscountService;
import com.shoes.service.dto.DiscountCreateDTO;
import com.shoes.service.dto.DiscountDTO;
import com.shoes.service.dto.DiscountResDTO;
import com.shoes.service.dto.DiscountShoesDetailsDTO;
import com.shoes.service.mapper.DiscountMapper;
import com.shoes.service.mapper.DiscountShoesDetailsMapper;
import com.shoes.service.mapper.ShoesMapper;
import com.shoes.util.DataUtils;
import com.shoes.util.Translator;
import com.shoes.web.rest.errors.BadRequestAlertException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Discount}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final Logger log = LoggerFactory.getLogger(DiscountServiceImpl.class);

    private final DiscountRepository discountRepository;

    private final DiscountMapper discountMapper;
    private final DiscountShoesDetailsRepository discountShoesDetailsRepository;

    private final DiscountShoesDetailsMapper discountShoesDetailsMapper;
    private final ShoesMapper shoesMapper;

    @Override
    public DiscountDTO save(DiscountCreateDTO discountDTO) {
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Request to save Discount : {}", discountDTO);
        Discount discount = discountMapper.toDiscountEntity(discountDTO);
        discount.setStatus(Constants.STATUS.ACTIVE);
        discount.setCreatedBy(loggedUser);
        discount.setLastModifiedBy(loggedUser);
        discountRepository.save(discount);
        List<DiscountShoesDetails> discountShoesDetailsList = discountDTO
            .getDiscountShoesDetailsDTOS()
            .stream()
            .map(this::mapDiscountShoesDetails)
            .collect(Collectors.toList());
        discountShoesDetailsList.forEach(discountShoesDetails -> {
            discountShoesDetails.setLastModifiedBy(loggedUser);
            discountShoesDetails.setDiscount(discount);
            if (Objects.isNull(discountShoesDetails.getId())) {
                discountShoesDetails.setCreatedBy(loggedUser);
                discountShoesDetails.setStatus(Constants.STATUS.ACTIVE);
            }
        });
        discountShoesDetailsRepository.saveAll(discountShoesDetailsList);
        return discountMapper.toDto(discount);
    }

    private DiscountShoesDetails mapDiscountShoesDetails(DiscountShoesDetailsDTO discountShoesDetailsDTO) {
        DiscountShoesDetails discountShoesDetails = new DiscountShoesDetails();
        discountShoesDetails.setShoesDetails(shoesMapper.toEntity(discountShoesDetailsDTO.getShoesDetails()));
        discountShoesDetails.setDiscountAmount(discountShoesDetailsDTO.getDiscountAmount());
        discountShoesDetails.setId(discountShoesDetailsDTO.getId());
        discountShoesDetails.setStatus(discountShoesDetailsDTO.getStatus());
        return discountShoesDetails;
    }

    @Override
    public DiscountDTO update(DiscountCreateDTO discountDTO, Long id) {
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Request to update Discount : {}", discountDTO);
        Discount discount = discountMapper.toDiscountEntity(discountDTO);
        discount.setId(id);
        discount.setLastModifiedBy(loggedUser);
        discountRepository.save(discount);
        List<DiscountShoesDetails> discountShoesDetailsList = discountShoesDetailsMapper.toEntity(
            discountDTO.getDiscountShoesDetailsDTOS()
        );
        discountShoesDetailsList.forEach(discountShoesDetails -> {
            discountShoesDetails.setLastModifiedBy(loggedUser);
        });
        discountShoesDetailsRepository.saveAll(discountShoesDetailsList);
        return discountMapper.toDto(discount);
    }

    @Override
    public Optional<DiscountDTO> partialUpdate(DiscountDTO discountDTO) {
        log.debug("Request to partially update Discount : {}", discountDTO);

        return discountRepository
            .findById(discountDTO.getId())
            .map(existingDiscount -> {
                discountMapper.partialUpdate(existingDiscount, discountDTO);

                return existingDiscount;
            })
            .map(discountRepository::save)
            .map(discountMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DiscountDTO> findAll() {
        log.debug("Request to get all Discounts");
        return discountRepository
            .findAllByStatus(Constants.STATUS.ACTIVE)
            .stream()
            .map(discountMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public DiscountResDTO findOne(Long id) {
        log.debug("Request to get Discount : {}", id);
        Discount discount = discountRepository.findByIdAndStatus(id, Constants.STATUS.ACTIVE);
        DiscountResDTO discountCreateDTO = discountMapper.toDiscountDTO(discount);
        List<DiscountShoesDetails> discountShoesDetailsList = discountShoesDetailsRepository.findAllByDiscount_IdAndStatus(
            id,
            Constants.STATUS.ACTIVE
        );
        discountCreateDTO.setDiscountShoesDetailsDTOS(discountShoesDetailsMapper.toDto(discountShoesDetailsList));
        return discountCreateDTO;
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Discount : {}", id);
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Discount discount = discountRepository
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException(Translator.toLocal(""), "abc", "abc"));
        discount.setStatus(Constants.STATUS.DELETE);
        discount.setLastModifiedBy(loggedUser);
        discount.setLastModifiedDate(Instant.now().plus(7, ChronoUnit.HOURS));
        discountRepository.save(discount);
    }

    @Override
    public List<DiscountDTO> search(String searchText) {
        if (StringUtils.isBlank(searchText)) {
            return findAll();
        }
        return discountRepository
            .searchByNameOrCode(DataUtils.makeLikeStr(DataUtils.likeSpecialToStr(searchText)))
            .stream()
            .map(discountMapper::toDto)
            .collect(Collectors.toList());
    }
}
