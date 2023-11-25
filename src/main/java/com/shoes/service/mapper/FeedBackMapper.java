package com.shoes.service.mapper;

import com.shoes.domain.FeedBack;
import com.shoes.domain.Shoes;
import com.shoes.domain.User;
import com.shoes.service.dto.FeedBackDTO;
import com.shoes.service.dto.ShoesDTO;
import com.shoes.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FeedBack} and its DTO {@link FeedBackDTO}.
 */
@Mapper(componentModel = "spring")
public interface FeedBackMapper extends EntityMapper<FeedBackDTO, FeedBack> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    FeedBackDTO toDto(FeedBack s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("shoesId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShoesDTO toDtoShoesId(Shoes shoes);
}
