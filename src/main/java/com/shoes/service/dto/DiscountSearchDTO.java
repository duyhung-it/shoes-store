package com.shoes.service.dto;

import java.time.Instant;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountSearchDTO {

    private Long id;
    private String code;
    private String name;
    private Integer discountMethod;
    private String discountMethodName;
    private String status;
    private Instant startDate;
    private Instant endDate;
    private Instant lastModifiedDate;
    private String lastModifiedBy;
}
