package com.shoes.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.shoes.domain.ShoesCategory} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShoesCategoryDTO implements Serializable {

    private Long id;

    @NotBlank(message = "error.shoes.category.code.notBlank")
    private String code;

    @NotBlank(message = "error.shoes.category.name.notBlank")
    private String name;

    @Valid
    private List<ShoesCategoryValueDTO> shoesCategoryValueDTOList;

    private Integer status;

    private String lastModifiedBy;

    private Instant lastModifiedDate;
}
