package com.shoes.service.dto;

import java.time.Instant;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoesCategoryUpdateDTO {

    private String code;

    @NotBlank(message = "error.shoes.category.name.notBlank")
    private String name;

    @Valid
    private List<ShoesCategoryValueDTO> shoesCategoryValueDTOList;

    private Integer status;

    private String lastModifiedBy;

    private Instant lastModifiedDate;
}
