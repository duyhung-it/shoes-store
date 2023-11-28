package com.shoes.service.mapper;

import com.shoes.domain.ReturnOrderDetails;
import com.shoes.service.dto.ReturnOrderDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReturnOrderDetails} and its DTO {@link ReturnOrderDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface ReturnOrderDetailsMapper extends EntityMapper<ReturnOrderDetailsDTO, ReturnOrderDetails> {}
