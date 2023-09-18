package com.shoes.service.mapper;

import com.shoes.domain.Cart;
import com.shoes.domain.CartDetails;
import com.shoes.domain.ShoesDetails;
import com.shoes.service.dto.CartDTO;
import com.shoes.service.dto.CartDetailsDTO;
import com.shoes.service.dto.ShoesDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CartDetails} and its DTO {@link CartDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface CartDetailsMapper extends EntityMapper<CartDetailsDTO, CartDetails> {
    @Mapping(target = "cart", source = "cart", qualifiedByName = "cartId")
    @Mapping(target = "shoesDetails", source = "shoesDetails", qualifiedByName = "shoesDetailsId")
    CartDetailsDTO toDto(CartDetails s);

    @Named("cartId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CartDTO toDtoCartId(Cart cart);

    @Named("shoesDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShoesDetailsDTO toDtoShoesDetailsId(ShoesDetails shoesDetails);
}
