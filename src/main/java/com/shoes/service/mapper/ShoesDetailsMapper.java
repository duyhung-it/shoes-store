package com.shoes.service.mapper;

import com.shoes.domain.*;
import com.shoes.service.dto.BrandDTO;
import com.shoes.service.dto.ShoesDTO;
import com.shoes.service.dto.ShoesDetailsCustomeDTO;
import com.shoes.service.dto.ShoesDetailsDTO;
import java.util.List;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for the entity {@link ShoesDetails} and its DTO {@link ShoesDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShoesDetailsMapper extends EntityMapper<ShoesDetailsDTO, ShoesDetails> {
    ShoesDetailsMapper INSTANCE = Mappers.getMapper(ShoesDetailsMapper.class);

    ShoesDetails toEntity(ShoesDetailsDTO shoesDetailsDTO);

    @Mapping(target = "shoes", source = "shoes", qualifiedByName = "shoesId")
    @Mapping(target = "brand", source = "brand", qualifiedByName = "brandId")
    @Mapping(target = "size", source = "size")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "tax", source = "tax")
    @Mapping(target = "import_price", source = "import_price")
    @Mapping(target = "description", source = "description")
    //    @Mapping(target = "images", ignore = true)
    ShoesDetailsDTO toDto(ShoesDetails s);

    @Named(value = "toShoesCustomDTO")
    ShoesDetailsCustomeDTO toShoesCustomDTO(ShoesDetails s);

    @Named(value = "toShoesCustomDTOList")
    @IterableMapping(qualifiedByName = "toShoesCustomDTO")
    List<ShoesDetailsCustomeDTO> toShoesCustomDTO(List<ShoesDetails> shoesDetails);

    @Named("shoesId")
    @Mapping(target = "id", source = "id")
    ShoesDTO toDtoShoesId(Shoes shoes);

    @Named("brandId")
    @Mapping(target = "id", source = "id")
    BrandDTO toDtoBrandId(Brand brand);
}
