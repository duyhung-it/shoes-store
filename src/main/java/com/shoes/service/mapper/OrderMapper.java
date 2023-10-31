package com.shoes.service.mapper;

import com.shoes.domain.Order;
import com.shoes.domain.User;
import com.shoes.service.dto.OrderCreateDTO;
import com.shoes.service.dto.OrderDTO;
import com.shoes.service.dto.OrderResDTO;
import com.shoes.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    //    @Mapping(target = "owner", source = "owner", qualifiedByName = "userId")
    OrderDTO toDto(Order s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("toOrderEntity")
    Order toOrderEntity(OrderCreateDTO orderCreateDTO);

    @Named("toOderResDTO")
    OrderResDTO toOderResDTO(Order order);
}
