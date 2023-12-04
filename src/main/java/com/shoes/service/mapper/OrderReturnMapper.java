package com.shoes.service.mapper;

import com.shoes.domain.Order;
import com.shoes.domain.OrderReturn;
import com.shoes.service.dto.OrderDTO;
import com.shoes.service.dto.OrderReturnDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderReturn} and its DTO {@link OrderReturnDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderReturnMapper extends EntityMapper<OrderReturnDTO, OrderReturn> {
    //    @Mapping(target = "order", source = "order", qualifiedByName = "orderId")
    OrderReturnDTO toDto(OrderReturn s);

    @Named("orderId")
    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    OrderDTO toDtoOrderId(Order order);
}
