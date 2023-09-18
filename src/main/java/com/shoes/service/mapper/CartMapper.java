package com.shoes.service.mapper;

import com.shoes.domain.Cart;
import com.shoes.domain.User;
import com.shoes.service.dto.CartDTO;
import com.shoes.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cart} and its DTO {@link CartDTO}.
 */
@Mapper(componentModel = "spring")
public interface CartMapper extends EntityMapper<CartDTO, Cart> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "userId")
    CartDTO toDto(Cart s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
