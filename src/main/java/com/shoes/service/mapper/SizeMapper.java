package com.shoes.service.mapper;

import com.shoes.domain.Size;
import com.shoes.service.dto.SizeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Size} and its DTO {@link SizeDTO}.
 */
@Mapper(componentModel = "spring")
public interface SizeMapper extends EntityMapper<SizeDTO, Size> {
    @Mapping(target = "status", source = "status")
    SizeDTO toDto(Size size);
}
