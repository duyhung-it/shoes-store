package com.shoes.service.mapper;

import com.shoes.domain.*;
import com.shoes.service.dto.BrandDTO;
import com.shoes.service.dto.ShoesDTO;
import com.shoes.service.dto.ShoesDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShoesDetails} and its DTO {@link ShoesDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShoesDetailsMapper extends EntityMapper<ShoesDetailsDTO, ShoesDetails> {
    @Mapping(target = "shoes", source = "shoes", qualifiedByName = "shoesId")
    @Mapping(target = "brand", source = "brand", qualifiedByName = "brandId")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "tax", source = "tax")
    @Mapping(target = "import_price", source = "import_price")
    @Mapping(target = "description", source = "description")
    ShoesDetailsDTO toDto(ShoesDetails s);

    @Named("shoesId")
    @Mapping(target = "id", source = "id")
    ShoesDTO toDtoShoesId(Shoes shoes);

    @Named("brandId")
    @Mapping(target = "id", source = "id")
    BrandDTO toDtoBrandId(Brand brand);
}
