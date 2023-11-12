package com.shoes.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.shoes.domain.Shoes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.shoes.domain.Shoes} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShoesDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private Integer status;

    private String createdBy;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Instant createdDate;

    private String lastModifiedBy;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Instant lastModifiedDate;

    private List<ShoesDetailsDTO> shoesDetails;
    private List<ShoesDetailsCustomeDTO> shoesDetailsCustomeDTOS;
    private Set<SizeDTO> sizeDTOS;
    private Set<ColorDTO> colorDTOS;
    public ShoesDTO(Shoes shoes) {
        this.id = shoes.getId();
        this.code = shoes.getCode();
        this.name = shoes.getName();
        this.lastModifiedBy = shoes.getLastModifiedBy();
        this.lastModifiedDate = shoes.getLastModifiedDate();
    }
}
