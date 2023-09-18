package com.shoes.service.mapper;

import com.shoes.domain.ShoesCategoryValue;
import com.shoes.domain.ShoesCategoryValueMapping;
import com.shoes.domain.ShoesDetails;
import com.shoes.service.dto.ShoesCategoryValueDTO;
import com.shoes.service.dto.ShoesCategoryValueMappingDTO;
import com.shoes.service.dto.ShoesDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShoesCategoryValueMapping} and its DTO {@link ShoesCategoryValueMappingDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShoesCategoryValueMappingMapper extends EntityMapper<ShoesCategoryValueMappingDTO, ShoesCategoryValueMapping> {
    @Mapping(target = "category", source = "category", qualifiedByName = "shoesCategoryValueId")
    @Mapping(target = "shoesDetails", source = "shoesDetails", qualifiedByName = "shoesDetailsId")
    ShoesCategoryValueMappingDTO toDto(ShoesCategoryValueMapping s);

    @Named("shoesCategoryValueId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShoesCategoryValueDTO toDtoShoesCategoryValueId(ShoesCategoryValue shoesCategoryValue);

    @Named("shoesDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShoesDetailsDTO toDtoShoesDetailsId(ShoesDetails shoesDetails);
}
