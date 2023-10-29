package com.shoes.service.mapper;

import com.shoes.domain.Discount;
import com.shoes.domain.DiscountShoesDetails;
import com.shoes.domain.Shoes;
import com.shoes.service.dto.DiscountDTO;
import com.shoes.service.dto.DiscountShoesDetailsDTO;
import com.shoes.service.dto.ShoesDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link DiscountShoesDetails} and its DTO {@link DiscountShoesDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DiscountShoesDetailsMapper extends EntityMapper<DiscountShoesDetailsDTO, DiscountShoesDetails> {
    //    @Mapping(target = "discount", source = "discount", qualifiedByName = "discountId")
    //    @Mapping(target = "shoesDetails", source = "shoesDetails", qualifiedByName = "shoesDetailsId")
    DiscountShoesDetailsDTO toDto(DiscountShoesDetails s);

    @Named("discountId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DiscountDTO toDtoDiscountId(Discount discount);

    @Named("shoesDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShoesDTO toDtoShoesDetailsId(Shoes shoes);
}
