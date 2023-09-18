package com.shoes.service.mapper;

import com.shoes.domain.Brand;
import com.shoes.service.dto.BrandDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Brand} and its DTO {@link BrandDTO}.
 */
@Mapper(componentModel = "spring")
public interface BrandMapper extends EntityMapper<BrandDTO, Brand> {
    @Named("toBrandEntity")
    @Mapping(target = "status", expression = "java(-1)")
    BrandDTO toBrandEntity(Brand brand);
}
