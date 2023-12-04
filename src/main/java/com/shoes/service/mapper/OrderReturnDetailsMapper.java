package com.shoes.service.mapper;

import com.shoes.domain.OrderDetails;
import com.shoes.domain.OrderReturn;
import com.shoes.domain.OrderReturnDetails;
import com.shoes.service.dto.OrderDetailsDTO;
import com.shoes.service.dto.OrderReturnDTO;
import com.shoes.service.dto.OrderReturnDetailsDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link OrderReturnDetails} and its DTO {@link OrderReturnDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderReturnDetailsMapper extends EntityMapper<OrderReturnDetailsDTO, OrderReturnDetails> {
    //    @Mapping(target = "orderDetails", source = "orderDetails", qualifiedByName = "orderDetailsId")
    //    @Mapping(target = "orderReturn", source = "orderReturn", qualifiedByName = "orderReturnId")
    OrderReturnDetailsDTO toDto(OrderReturnDetails s);

    @Named("orderDetailsId")
    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    OrderDetailsDTO toDtoOrderDetailsId(OrderDetails orderDetails);

    @Named("orderReturnId")
    @BeanMapping(ignoreByDefault = true)
    //    @Mapping(target = "id", source = "id")
    OrderReturnDTO toDtoReturnOrderId(OrderReturn orderReturn);
}
