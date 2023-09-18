package com.shoes.service.mapper;

import com.shoes.domain.ShoesCategory;
import com.shoes.service.dto.ShoesCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShoesCategory} and its DTO {@link ShoesCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShoesCategoryMapper extends EntityMapper<ShoesCategoryDTO, ShoesCategory> {}
