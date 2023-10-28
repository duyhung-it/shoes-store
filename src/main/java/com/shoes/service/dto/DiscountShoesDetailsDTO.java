package com.shoes.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.shoes.domain.DiscountShoesDetails} entity.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DiscountShoesDetailsDTO implements Serializable {

    private Long id;

    private BigDecimal discountAmount;

    private DiscountDTO discount;

    private ShoesDTO shoesDTO;
}
