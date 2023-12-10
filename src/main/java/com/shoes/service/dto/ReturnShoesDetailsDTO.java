package com.shoes.service.dto;

import com.shoes.domain.OrderReturnDetails;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.shoes.domain.ReturnShoesDetails} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ReturnShoesDetailsDTO implements Serializable {

    private Long id;

    private Integer quantity;

    private Integer price;

    private Integer discount;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private ShoesDetailsDTO shoesDetails;
    private Long shoesDetailsId;

    private OrderReturnDetailsDTO orderReturnDetailsDTO;
}
