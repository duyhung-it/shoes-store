package com.shoes.service.dto;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderReturnSearchResDTO {

    private Long id;
    private String code;
    private String login;
    private String phone;
    private Integer status;
    private String lastModifiedBy;
    private Instant createdDate;
}
