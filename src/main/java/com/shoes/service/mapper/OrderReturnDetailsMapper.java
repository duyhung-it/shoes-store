package com.shoes.service.mapper;

import com.shoes.domain.OrderDetails;
import com.shoes.domain.OrderReturnDetails;
import com.shoes.domain.ReturnOrder;
import com.shoes.service.dto.OrderDetailsDTO;
import com.shoes.service.dto.OrderReturnDetailsDTO;
import com.shoes.service.dto.ReturnOrderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderReturnDetails} and its DTO {@link OrderReturnDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderReturnDetailsMapper extends EntityMapper<OrderReturnDetailsDTO, OrderReturnDetails> {
    @Mapping(target = "orderDetails", source = "orderDetails", qualifiedByName = "orderDetailsId")
    @Mapping(target = "returnOrder", source = "returnOrder", qualifiedByName = "returnOrderId")
    OrderReturnDetailsDTO toDto(OrderReturnDetails s);

    @Named("orderDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderDetailsDTO toDtoOrderDetailsId(OrderDetails orderDetails);

    @Named("returnOrderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ReturnOrderDTO toDtoReturnOrderId(ReturnOrder returnOrder);
}
