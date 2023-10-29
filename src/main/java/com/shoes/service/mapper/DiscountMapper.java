package com.shoes.service.mapper;

import com.shoes.domain.Discount;
import com.shoes.service.dto.DiscountCreateDTO;
import com.shoes.service.dto.DiscountDTO;
import com.shoes.service.dto.DiscountResDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Discount} and its DTO {@link DiscountDTO}.
 */
@Mapper(componentModel = "spring")
public interface DiscountMapper extends EntityMapper<DiscountDTO, Discount> {
    @Named("toDiscountEntity")
    Discount toDiscountEntity(DiscountCreateDTO createDTO);

    @Named("toDiscountDTO")
    DiscountResDTO toDiscountDTO(Discount discount);
}
