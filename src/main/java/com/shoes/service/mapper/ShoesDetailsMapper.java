package com.shoes.service.mapper;

import com.shoes.domain.Brand;
import com.shoes.domain.Shoes;
import com.shoes.domain.ShoesDetails;
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
    ShoesDetailsDTO toDto(ShoesDetails s);

    @Named("shoesId")
    @Mapping(target = "id", source = "id")
    ShoesDTO toDtoShoesId(Shoes shoes);

    @Named("brandId")
    @Mapping(target = "id", source = "id")
    BrandDTO toDtoBrandId(Brand brand);
}
