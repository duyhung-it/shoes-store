package com.shoes.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.shoes.domain.OrderReturnDetails} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderReturnDetailsDTO implements Serializable {

    private Long id;

    private Integer returnQuantity;

    private Integer errorQuantity;

    private Integer type;

    private String reason;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private List<ReturnShoesDetailsDTO> returnShoesDetails;

    private OrderDetailsDTO orderDetails;
    private Long orderDetailsId;

    private OrderReturnDTO orderReturn;

    public OrderReturnDetailsDTO(Long id) {
        this.id = id;
    }
}
