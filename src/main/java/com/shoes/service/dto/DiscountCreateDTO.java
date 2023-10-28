package com.shoes.service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DiscountCreateDTO {

    private Long id;

    private String code;

    private String name;

    private Instant startDate;

    private Instant endDate;

    private Integer discountMethod;

    private BigDecimal discountAmount;

    private Integer discountStatus;

    private List<DiscountShoesDetailsDTO> discountShoesDetailsDTOS;
}