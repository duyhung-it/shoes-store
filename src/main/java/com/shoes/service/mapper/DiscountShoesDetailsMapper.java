package com.shoes.service.mapper;

import com.shoes.domain.Discount;
import com.shoes.domain.DiscountShoesDetails;
import com.shoes.domain.ShoesDetails;
import com.shoes.service.dto.DiscountDTO;
import com.shoes.service.dto.DiscountShoesDetailsDTO;
import com.shoes.service.dto.ShoesDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DiscountShoesDetails} and its DTO {@link DiscountShoesDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DiscountShoesDetailsMapper extends EntityMapper<DiscountShoesDetailsDTO, DiscountShoesDetails> {
    @Mapping(target = "discount", source = "discount", qualifiedByName = "discountId")
    @Mapping(target = "shoesDetails", source = "shoesDetails", qualifiedByName = "shoesDetailsId")
    DiscountShoesDetailsDTO toDto(DiscountShoesDetails s);

    @Named("discountId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DiscountDTO toDtoDiscountId(Discount discount);

    @Named("shoesDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShoesDetailsDTO toDtoShoesDetailsId(ShoesDetails shoesDetails);
}
