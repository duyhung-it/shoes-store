package com.shoes.service.dto;

import com.shoes.domain.Payment;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResDTO {

    private Long id;

    private String code;

    private String address;

    private String phone;

    private Integer paymentMethod;
    private Integer paymentStatus;

    private BigDecimal shipPrice;

    private BigDecimal totalPrice;

    private String receivedBy;

    private Instant receivedDate;

    private Instant shippedDate;

    private Integer status;
    private UserDTO owner;
    private Payment payment;
    private String lastModifiedBy;
    private Instant lastModifiedDate;
    private List<OrderDetailsDTO> orderDetailsDTOList;
}
