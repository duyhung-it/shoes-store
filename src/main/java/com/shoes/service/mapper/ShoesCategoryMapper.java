package com.shoes.service.mapper;

import com.shoes.config.Constants;
import com.shoes.domain.ShoesCategory;
import com.shoes.domain.ShoesCategoryValue;
import com.shoes.repository.ShoesCategoryValueRepository;
import com.shoes.service.dto.ShoesCategoryDTO;
import com.shoes.service.dto.ShoesCategoryValueDTO;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mapper for the entity {@link ShoesCategory} and its DTO {@link ShoesCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public abstract class ShoesCategoryMapper implements EntityMapper<ShoesCategoryDTO, ShoesCategory> {

    private ShoesCategoryValueRepository shoesCategoryValueRepository;
    private ShoesCategoryValueMapper shoesCategoryValueMapper;

    @Autowired
    public void setShoesCategoryValueRepository(ShoesCategoryValueRepository shoesCategoryValueRepository) {
        this.shoesCategoryValueRepository = shoesCategoryValueRepository;
    }

    @Autowired
    public void setShoesCategoryValueMapper(ShoesCategoryValueMapper shoesCategoryValueMapper) {
        this.shoesCategoryValueMapper = shoesCategoryValueMapper;
    }

    @AfterMapping
    public void loadShoesCategoryValueDTO(ShoesCategory shoesCategory, @MappingTarget ShoesCategoryDTO shoesCategoryDTO) {
        List<ShoesCategoryValue> shoesCategoryValues = shoesCategoryValueRepository.findAllByCategory_IdAndStatus(
            shoesCategory.getId(),
            Constants.STATUS.ACTIVE
        );
        List<ShoesCategoryValueDTO> shoesCategoryValueDTOS = shoesCategoryValueMapper.toDto(shoesCategoryValues);
        shoesCategoryDTO.setShoesCategoryValueDTOList(shoesCategoryValueDTOS);
    }

    public abstract ShoesCategory toEntity(ShoesCategoryDTO shoesCategoryDTO);

    public abstract ShoesCategoryDTO toDto(ShoesCategory shoesCategory);
}
