package com.shoes.service.dto;

import com.shoes.domain.Address;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the {@link com.shoes.domain.Order} entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OrderDTO implements Serializable {

    private Long id;

    private String code;

    private String address;

    private String phone;

    private Integer paymentMethod;

    private BigDecimal shipPrice;

    private BigDecimal totalPrice;

    private String receivedBy;

    private Instant receivedDate;

    private Instant shippedDate;

    private Integer status;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private UserDTO owner;
    private AddressDTO ownerAddress;
}
