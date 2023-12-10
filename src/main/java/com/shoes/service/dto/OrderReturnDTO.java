package com.shoes.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.shoes.domain.OrderReturn} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderReturnDTO implements Serializable {

    private Long id;

    private String code;

    private String title;

    private String description;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private OrderDTO order;

    private List<OrderReturnDetailsDTO> orderReturnDetailsDTOS;
}
