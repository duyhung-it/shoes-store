package com.shoes.service.dto;

import com.shoes.util.DataUtils;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDTO {

    private Long id;

    private String code;

    private String address;

    private String phone;

    private Integer paymentMethod;
    private Integer paymentStatus;

    private BigDecimal shipPrice;

    private BigDecimal totalPrice;

    private String receivedBy;

    private String receivedDate;

    private String shippedDate;

    private Integer status;
    private UserDTO owner;
    private List<OrderDetailsDTO> orderDetailsDTOList;

    public Instant getReceivedDate() {
        return DataUtils.getStartOfDay_yyyy_MM_dd_HH_mm_ss(receivedDate);
    }

    public Instant getShippedDate() {
        return DataUtils.getEndOfDay_yyyy_MM_dd_HH_mm_ss(shippedDate);
    }
}
