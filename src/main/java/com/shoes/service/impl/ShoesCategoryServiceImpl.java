package com.shoes.service.impl;

import com.shoes.config.Constants;
import com.shoes.domain.ShoesCategory;
import com.shoes.domain.ShoesCategoryValue;
import com.shoes.repository.ShoesCategoryRepository;
import com.shoes.repository.ShoesCategoryValueRepository;
import com.shoes.service.ShoesCategoryService;
import com.shoes.service.dto.*;
import com.shoes.service.mapper.ShoesCategoryMapper;
import com.shoes.service.mapper.ShoesCategoryValueMapper;
import com.shoes.util.DataUtils;
import com.shoes.util.SecurityUtils;
import com.shoes.util.Translator;
import com.shoes.web.rest.errors.BadRequestAlertException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShoesCategory}.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ShoesCategoryServiceImpl implements ShoesCategoryService {

    private final Logger log = LoggerFactory.getLogger(ShoesCategoryServiceImpl.class);

    private final ShoesCategoryRepository shoesCategoryRepository;

    private final ShoesCategoryMapper shoesCategoryMapper;
    private final ShoesCategoryValueMapper shoesCategoryValueMapperMapper;
    private final ShoesCategoryValueRepository shoesCategoryValueRepository;
    private static final String ENTITY_NAME = "shoesCategory";

    @Override
    public ShoesCategoryDTO save(ShoesCategoryDTO shoesCategoryDTO) {
        log.debug("Request to save ShoesCategory : {}", shoesCategoryDTO);
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        DataUtils.trimValue(shoesCategoryDTO);
        validateCreate(shoesCategoryDTO);
        ShoesCategory shoesCategory = shoesCategoryMapper.toEntity(shoesCategoryDTO);
        shoesCategory.setCreatedBy(loggedUser);
        shoesCategory.setCode(DataUtils.toUpperCase(shoesCategory.getCode()));
        shoesCategory.setLastModifiedBy(loggedUser);
        shoesCategory.setStatus(Constants.STATUS.ACTIVE);
        shoesCategory.setId(null);
        shoesCategoryRepository.save(shoesCategory);
        List<ShoesCategoryValueDTO> shoesCategoryValueDTOList = shoesCategoryDTO.getShoesCategoryValueDTOList();
        saveCategoryValues(shoesCategoryValueDTOList, shoesCategory);
        return shoesCategoryMapper.toDto(shoesCategory);
    }

    private void validateCreate(ShoesCategoryDTO shoesCategoryDTO) {
        Long countByCode = shoesCategoryRepository.countByCodeAndStatus(shoesCategoryDTO.getCode(), Constants.STATUS.ACTIVE);
        if (countByCode > 0) {
            throw new BadRequestAlertException(Translator.toLocal("error.shoes.category.code.exist"), ENTITY_NAME, "code");
        }
    }

    @Override
    public ShoesCategoryDTO update(ShoesCategoryUpdateDTO shoesCategoryDTO, Long idShoesCategory) {
        log.debug("Request to update ShoesCategory : {}", shoesCategoryDTO);
        String loggedUser = SecurityUtils.getCurrentUserLogin().orElse("anonymousUser");
        DataUtils.trimValue(shoesCategoryDTO);
        ShoesCategory shoesCategory = validateUpdate(shoesCategoryDTO, idShoesCategory);
        shoesCategory.setName(shoesCategoryDTO.getName());
        shoesCategory.setLastModifiedBy(loggedUser);
        shoesCategoryRepository.save(shoesCategory);
        List<ShoesCategoryValueDTO> shoesCategoryValueDTOList = shoesCategoryDTO.getShoesCategoryValueDTOList();
        saveCategoryValues(shoesCategoryValueDTOList, shoesCategory);
        return shoesCategoryMapper.toDto(shoesCategory);
    }

    private void saveCategoryValues(List<ShoesCategoryValueDTO> shoesCategoryValueDTOList, ShoesCategory shoesCategory) {
        String loggedUser = SecurityUtils.getCurrentUserLogin().orElse("");
        if (CollectionUtils.isNotEmpty(shoesCategoryValueDTOList)) {
            List<ShoesCategoryValue> shoesCategoryValues = shoesCategoryValueMapperMapper.toEntity(shoesCategoryValueDTOList);
            for (ShoesCategoryValue shoesCategoryValue : shoesCategoryValues) {
                DataUtils.trimValue(shoesCategoryValue);
                if (Objects.isNull(shoesCategoryValue.getId())) {
                    shoesCategoryValue.setCreatedBy(loggedUser);
                }
                shoesCategoryValue.setLastModifiedBy(loggedUser);
                shoesCategoryValue.setCategory(shoesCategory);
                if (!Objects.equals(shoesCategoryValue.getStatus(), Constants.STATUS.DELETE)) {
                    shoesCategoryValue.setStatus(Constants.STATUS.ACTIVE);
                }
            }
            shoesCategoryValueRepository.saveAll(shoesCategoryValues);
        }
    }

    private ShoesCategory validateUpdate(ShoesCategoryUpdateDTO shoesCategoryDTO, Long idShoesCategory) {
        Optional<ShoesCategory> shoesCategoryOptional = shoesCategoryRepository.findByIdAndStatus(idShoesCategory, Constants.STATUS.ACTIVE);
        return shoesCategoryOptional.orElseThrow(() ->
            new BadRequestAlertException(Translator.toLocal("error.shoes.category.not.exist"), ENTITY_NAME, "exist")
        );
    }

    @Override
    public Optional<ShoesCategoryDTO> partialUpdate(ShoesCategoryDTO shoesCategoryDTO) {
        log.debug("Request to partially update ShoesCategory : {}", shoesCategoryDTO);

        return shoesCategoryRepository
            .findById(shoesCategoryDTO.getId())
            .map(existingShoesCategory -> {
                shoesCategoryMapper.partialUpdate(existingShoesCategory, shoesCategoryDTO);

                return existingShoesCategory;
            })
            .map(shoesCategoryRepository::save)
            .map(shoesCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ShoesCategorySearchResDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ShoesCategories");
        return shoesCategoryRepository.search(null, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public ShoesCategoryDTO findOne(Long id) {
        log.debug("Request to get ShoesCategory : {}", id);
        return shoesCategoryRepository
            .findByIdAndStatus(id, Constants.STATUS.ACTIVE)
            .map(shoesCategoryMapper::toDto)
            .orElseThrow(() -> new BadRequestAlertException(Translator.toLocal("error.shoes.category.not.exist"), ENTITY_NAME, "exist"));
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShoesCategory : {}", id);
        String loggedUser = SecurityUtils.getCurrentUserLogin().orElse("anonymousUser");
        Optional<ShoesCategory> shoesCategoryOptional = shoesCategoryRepository.findByIdAndStatus(id, Constants.STATUS.ACTIVE);
        shoesCategoryOptional.orElseThrow(() ->
            new BadRequestAlertException(Translator.toLocal("error.shoes.category.not.exist"), ENTITY_NAME, "exist")
        );
        shoesCategoryRepository.updateStatus(id, Constants.STATUS.DELETE, loggedUser, Instant.now());
    }

    @Override
    public Page<ShoesCategorySearchResDTO> search(ShoesCategorySearchReqDTO shoesCategorySearchReqDTO, Pageable pageable) {
        return shoesCategoryRepository.search(shoesCategorySearchReqDTO.getSearchText(), pageable);
    }
}
