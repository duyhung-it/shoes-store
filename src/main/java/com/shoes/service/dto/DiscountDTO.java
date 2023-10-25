package com.shoes.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.shoes.domain.Discount} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiscountDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private Instant startDate;

    private Instant endDate;

    private Integer discountMethod;

    private BigDecimal discountAmount;

    private Integer discountStatus;
}
