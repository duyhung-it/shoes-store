package com.shoes.service.dto;

import java.time.Instant;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoesCategorySearchResDTO {

    private Long id;

    @NotBlank(message = "error.shoes.category.code.notBlank")
    private String code;

    @NotBlank(message = "error.shoes.category.name.notBlank")
    private String name;

    private Integer status;

    private String lastModifiedBy;

    private Instant lastModifiedDate;
}
