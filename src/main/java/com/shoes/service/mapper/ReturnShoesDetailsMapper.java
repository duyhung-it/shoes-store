package com.shoes.service.mapper;

import com.shoes.domain.ReturnOrderDetails;
import com.shoes.domain.ReturnShoesDetails;
import com.shoes.domain.ShoesDetails;
import com.shoes.service.dto.ReturnOrderDetailsDTO;
import com.shoes.service.dto.ReturnShoesDetailsDTO;
import com.shoes.service.dto.ShoesDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReturnShoesDetails} and its DTO {@link ReturnShoesDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReturnShoesDetailsMapper extends EntityMapper<ReturnShoesDetailsDTO, ReturnShoesDetails> {
    @Mapping(target = "shoesDetails", source = "shoesDetails", qualifiedByName = "shoesDetailsId")
    @Mapping(target = "returnOrderDetails", source = "returnOrderDetails", qualifiedByName = "returnOrderDetailsId")
    ReturnShoesDetailsDTO toDto(ReturnShoesDetails s);

    @Named("shoesDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShoesDetailsDTO toDtoShoesDetailsId(ShoesDetails shoesDetails);

    @Named("returnOrderDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReturnOrderDetailsDTO toDtoReturnOrderDetailsId(ReturnOrderDetails returnOrderDetails);
}
