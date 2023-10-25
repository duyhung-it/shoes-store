package com.shoes.service.impl;

import com.shoes.config.Constants;
import com.shoes.domain.Discount;
import com.shoes.domain.DiscountShoesDetails;
import com.shoes.repository.DiscountRepository;
import com.shoes.repository.DiscountShoesDetailsRepository;
import com.shoes.service.DiscountService;
import com.shoes.service.dto.DiscountCreateDTO;
import com.shoes.service.dto.DiscountDTO;
import com.shoes.service.mapper.DiscountMapper;
import com.shoes.service.mapper.DiscountShoesDetailsMapper;
import com.shoes.util.DataUtils;
import com.shoes.util.Translator;
import com.shoes.web.rest.errors.BadRequestAlertException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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

    @Override
    public DiscountDTO save(DiscountCreateDTO discountDTO) {
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Request to save Discount : {}", discountDTO);
        Discount discount = discountMapper.toDiscountEntity(discountDTO);
        discount.setStatus(Constants.STATUS.ACTIVE);
        discount.setCreatedBy(loggedUser);
        discount.setLastModifiedBy(loggedUser);
        discountRepository.save(discount);
        List<DiscountShoesDetails> discountShoesDetailsList = discountShoesDetailsMapper.toEntity(
            discountDTO.getDiscountShoesDetailsDTOS()
        );
        discountShoesDetailsList.forEach(discountShoesDetails -> {
            discountShoesDetails.setDiscount(discount);
            discountShoesDetails.setLastModifiedBy(loggedUser);
            discountShoesDetails.setCreatedBy(loggedUser);
            discountShoesDetails.setStatus(Constants.STATUS.ACTIVE);
        });
        discountShoesDetailsRepository.saveAll(discountShoesDetailsList);
        return discountMapper.toDto(discount);
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
    public Optional<DiscountDTO> findOne(Long id) {
        log.debug("Request to get Discount : {}", id);
        return discountRepository.findById(id).map(discountMapper::toDto);
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
        return discountRepository
            .searchByNameOrCode(DataUtils.likeSpecialToStr(searchText))
            .stream()
            .map(discountMapper::toDto)
            .collect(Collectors.toList());
    }
}
