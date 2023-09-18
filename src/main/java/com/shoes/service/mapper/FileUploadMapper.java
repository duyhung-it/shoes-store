package com.shoes.service.mapper;

import com.shoes.domain.FileUpload;
import com.shoes.service.dto.FileUploadDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FileUpload} and its DTO {@link FileUploadDTO}.
 */
@Mapper(componentModel = "spring")
public interface FileUploadMapper extends EntityMapper<FileUploadDTO, FileUpload> {}
