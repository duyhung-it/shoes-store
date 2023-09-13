package com.shoes.service.mapper;

import com.shoes.domain.Shoes;
import com.shoes.service.dto.ShoesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Shoes} and its DTO {@link ShoesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShoesMapper extends EntityMapper<ShoesDTO, Shoes> {}
