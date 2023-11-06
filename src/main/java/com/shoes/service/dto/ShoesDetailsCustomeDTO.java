package com.shoes.service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoesDetailsCustomeDTO {

    private Long id;

    private String code;

    private BigDecimal price;

    private BigDecimal import_price;

    private BigDecimal tax;

    private Long quantity;

    private String description;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ShoesDTO shoes;

    private BrandDTO brand;

    private SizeDTO size;

    private List<SizeDTO> sizes;

    private ColorDTO color;
}
