package com.shoes.service.mapper;

import com.shoes.domain.FileUpload;
import com.shoes.domain.ShoesDetails;
import com.shoes.domain.ShoesFileUploadMapping;
import com.shoes.service.dto.FileUploadDTO;
import com.shoes.service.dto.ShoesDetailsDTO;
import com.shoes.service.dto.ShoesFileUploadMappingDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShoesFileUploadMapping} and its DTO {@link ShoesFileUploadMappingDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShoesFileUploadMappingMapper extends EntityMapper<ShoesFileUploadMappingDTO, ShoesFileUploadMapping> {
    @Mapping(target = "fileUpload", source = "fileUpload", qualifiedByName = "fileUploadId")
    @Mapping(target = "shoesDetails", source = "shoesDetails", qualifiedByName = "shoesDetailsId")
    ShoesFileUploadMappingDTO toDto(ShoesFileUploadMapping s);

    @Named("fileUploadId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FileUploadDTO toDtoFileUploadId(FileUpload fileUpload);

    @Named("shoesDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ShoesDetailsDTO toDtoShoesDetailsId(ShoesDetails shoesDetails);
}
