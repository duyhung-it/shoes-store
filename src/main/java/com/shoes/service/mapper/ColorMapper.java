package com.shoes.service.mapper;

import com.shoes.domain.Color;
import com.shoes.service.dto.ColorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Color} and its DTO {@link ColorDTO}.
 */
@Mapper(componentModel = "spring")
public interface ColorMapper extends EntityMapper<ColorDTO, Color> {}
