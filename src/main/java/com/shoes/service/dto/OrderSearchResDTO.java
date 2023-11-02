package com.shoes.service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSearchResDTO {

    private Long id;
    private String code;
    private Long idCustomer;
    private String customer;
    private String phone;
    private BigDecimal totalPrice;
    private Integer status;
    private Instant createdDate;
    private String lastModifiedBy;
}
