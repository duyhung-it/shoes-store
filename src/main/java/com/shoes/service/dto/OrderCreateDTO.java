package com.shoes.service.dto;

import com.shoes.util.DataUtils;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateDTO {

    private Long id;

    @NotBlank
    private String code;

    @NotNull
    private AddressDTO userAddress;

    @NotBlank
    private String phone;

    @NotNull
    private Integer paymentMethod;

    private Integer paymentStatus;

    private BigDecimal shipPrice;

    private BigDecimal totalPrice;

    private String receivedBy;

    private String receivedDate;

    private String shippedDate;

    private Integer status;
    private UserDTO owner;

    @NotEmpty
    @Valid
    private List<OrderDetailsDTO> orderDetailsDTOList = new ArrayList<>();

    public Instant getReceivedDate() {
        return DataUtils.getStartOfDay_yyyy_MM_dd_HH_mm_ss(receivedDate);
    }

    public Instant getShippedDate() {
        return DataUtils.getEndOfDay_yyyy_MM_dd_HH_mm_ss(shippedDate);
    }
}
