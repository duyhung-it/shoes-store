package com.shoes.service.impl;

import com.shoes.config.Constants;
import com.shoes.domain.Brand;
import com.shoes.domain.Discount;
import com.shoes.domain.DiscountShoesDetails;
import com.shoes.repository.BrandRepository;
import com.shoes.repository.DiscountRepository;
import com.shoes.repository.DiscountShoesDetailsRepository;
import com.shoes.service.DiscountService;
import com.shoes.service.dto.*;
import com.shoes.service.mapper.DiscountMapper;
import com.shoes.service.mapper.DiscountShoesDetailsMapper;
import com.shoes.service.mapper.ShoesMapper;
import com.shoes.util.DataUtils;
import com.shoes.util.Translator;
import com.shoes.web.rest.errors.BadRequestAlertException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
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
    private final ShoesMapper shoesMapper;
    private static final String ENTITY_NAME = "discount";
    private final String baseCode = "KM";
    private final BrandRepository brandRepository;

    @Override
    public DiscountDTO save(DiscountCreateDTO discountDTO) {
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Request to save Discount : {}", discountDTO);
        if (discountDTO.getStartDate().isAfter(discountDTO.getEndDate())) {
            throw new BadRequestAlertException("Ngày hiệu lực không được lớn hơn ngày hết hiệu lực", ENTITY_NAME, "date");
        }
        if (Constants.DISCOUNT_METHOD.TOTAL_PERCENT.equals(discountDTO.getDiscountMethod())) {
            if (discountDTO.getDiscountAmount().doubleValue() > 100 || discountDTO.getDiscountAmount().doubleValue() <= 0) {
                throw new BadRequestAlertException("Số % giảm phải lớn hơn 0 và nhỏ hơn 100", ENTITY_NAME, "date");
            }
        } else if (Constants.DISCOUNT_METHOD.PER_PERCENT.equals(discountDTO.getDiscountMethod())) {
            for (DiscountShoesDetailsDTO discountShoesDetails : discountDTO.getDiscountShoesDetailsDTOS()) {
                if (
                    discountShoesDetails.getDiscountAmount().doubleValue() > 100 ||
                    discountShoesDetails.getDiscountAmount().doubleValue() <= 0
                ) {
                    throw new BadRequestAlertException("Số % giảm phải lớn hơn 0 và nhỏ hơn 100", ENTITY_NAME, "date");
                }
            }
        }
        Discount discount = discountMapper.toDiscountEntity(discountDTO);
        discount.setCode(generateCode());
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
        for (DiscountShoesDetails discountShoesDetails : discountShoesDetailsList) {
            DiscountShoesDetails discountShoesDetails1 = discountShoesDetailsRepository.findByShoesIdAndStatus(
                discountShoesDetails.getShoesDetails().getId(),
                discountShoesDetails.getBrandId()
            );
            if (Objects.nonNull(discountShoesDetails1)) {
                throw new BadRequestAlertException("Giày đã được sử dụng trong chương trình giảm giá khác!", ENTITY_NAME, "used");
            }
        }
        discountShoesDetailsRepository.saveAll(discountShoesDetailsList);
        return discountMapper.toDto(discount);
    }

    private DiscountShoesDetails mapDiscountShoesDetails(DiscountShoesDetailsDTO discountShoesDetailsDTO) {
        DiscountShoesDetails discountShoesDetails = new DiscountShoesDetails();
        discountShoesDetails.setShoesDetails(shoesMapper.toEntity(discountShoesDetailsDTO.getShoesDetails()));
        discountShoesDetails.setDiscountAmount(discountShoesDetailsDTO.getDiscountAmount());
        discountShoesDetails.setId(discountShoesDetailsDTO.getId());
        discountShoesDetails.setStatus(discountShoesDetailsDTO.getStatus());
        discountShoesDetails.setBrandId(discountShoesDetailsDTO.getBrandId());
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
        for (DiscountShoesDetails discountShoesDetails : discountShoesDetailsList) {
            DiscountShoesDetails discountShoesDetails1 = discountShoesDetailsRepository.findByShoesIdAndStatus(
                discountShoesDetails.getShoesDetails().getId(),
                discountShoesDetails.getBrandId()
            );
            if (Objects.nonNull(discountShoesDetails1) && !Objects.equals(discountShoesDetails.getId(), discountShoesDetails1.getId())) {
                throw new BadRequestAlertException("Giày đã được sử dụng trong chương trình giảm giá khác!", ENTITY_NAME, "used");
            }
        }
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
        if (DataUtils.isNull(discount)) {
            throw new BadRequestAlertException("Chương trình khuyến mại không tồn tại", "discount", "exist");
        }
        DiscountResDTO discountCreateDTO = discountMapper.toDiscountDTO(discount);
        List<DiscountShoesDetails> discountShoesDetailsList = discountShoesDetailsRepository.findAllByDiscount_IdAndStatus(
            id,
            Constants.STATUS.ACTIVE
        );
        Set<Long> brandId = discountShoesDetailsList.stream().map(DiscountShoesDetails::getBrandId).collect(Collectors.toSet());
        List<Brand> brands = brandRepository.findByIdInAndStatus(new ArrayList<Long>(brandId), Constants.STATUS.ACTIVE);
        List<DiscountShoesDetailsDTO> detailsDTOList = discountShoesDetailsMapper.toDto(discountShoesDetailsList);
        for (DiscountShoesDetailsDTO shoesDetails : detailsDTOList) {
            for (Brand brand : brands) {
                if (Objects.equals(brand.getId(), shoesDetails.getBrandId())) {
                    shoesDetails.setBrandName(brand.getName());
                    break;
                }
            }
        }
        discountCreateDTO.setDiscountShoesDetailsDTOS(detailsDTOList);
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
    public List<DiscountSearchDTO> search(String searchText) {
        return discountRepository.search(searchText);
    }

    public String generateCode() {
        Instant currentDateTime = DataUtils.getCurrentDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = formatter.format(LocalDate.ofInstant(currentDateTime, ZoneId.of("UTC")));
        String dateString = DataUtils.makeLikeStr(formattedDate);
        List<Discount> list = discountRepository.findByCreatedDate(dateString);
        int numberInDay = list.size() + 1;
        String code = DataUtils.replaceSpecialCharacters(formattedDate);
        return baseCode + code + numberInDay;
    }
}
