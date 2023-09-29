package com.shoes.service.dto;

import java.io.Serializable;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.shoes.domain.FileUpload} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FileUploadDTO implements Serializable {

    private Long id;
    private String path;
    private String name;
    private String lastModifiedBy;
    private Integer status;
    private Instant lastModifiedDate;
}
