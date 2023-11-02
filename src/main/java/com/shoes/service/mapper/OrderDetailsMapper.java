package com.shoes.service.mapper;

import com.shoes.domain.Order;
import com.shoes.domain.OrderDetails;
import com.shoes.domain.ShoesDetails;
import com.shoes.service.dto.OrderDTO;
import com.shoes.service.dto.OrderDetailsDTO;
import com.shoes.service.dto.ShoesDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderDetails} and its DTO {@link OrderDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderDetailsMapper extends EntityMapper<OrderDetailsDTO, OrderDetails> {
    @Mapping(target = "order", source = "order", qualifiedByName = "orderId")
    @Mapping(target = "shoesDetails", source = "shoesDetails", qualifiedByName = "shoesDetailsId")
    OrderDetailsDTO toDto(OrderDetails s);

    @Named("orderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDTO toDtoOrderId(Order order);

    @Named("shoesDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShoesDetailsDTO toDtoShoesDetailsId(ShoesDetails shoesDetails);
}
