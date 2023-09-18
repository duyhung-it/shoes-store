package com.shoes.service.mapper;

import com.shoes.domain.ShoesCategory;
import com.shoes.domain.ShoesCategoryValue;
import com.shoes.service.dto.ShoesCategoryDTO;
import com.shoes.service.dto.ShoesCategoryValueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShoesCategoryValue} and its DTO {@link ShoesCategoryValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShoesCategoryValueMapper extends EntityMapper<ShoesCategoryValueDTO, ShoesCategoryValue> {
    @Mapping(target = "category", source = "category", qualifiedByName = "shoesCategoryId")
    ShoesCategoryValueDTO toDto(ShoesCategoryValue s);

    @Named("shoesCategoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShoesCategoryDTO toDtoShoesCategoryId(ShoesCategory shoesCategory);
}
