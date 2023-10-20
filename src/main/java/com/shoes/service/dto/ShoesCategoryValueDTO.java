package com.shoes.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A DTO for the {@link com.shoes.domain.ShoesCategoryValue} entity.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ShoesCategoryValueDTO implements Serializable {

    private Long id;

    @NotBlank(message = "error.shoes.category.value.not.blank")
    private String value;

    private String description;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ShoesCategoryDTO category;
}
