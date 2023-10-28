package com.shoes.service.dto;

import com.shoes.util.DataUtils;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DiscountCreateDTO {

    private Long id;

    private String code;

    private String name;

    private String startDate;

    private String endDate;

    private Integer discountMethod;

    private BigDecimal discountAmount;

    private Integer discountStatus;

    private List<DiscountShoesDetailsDTO> discountShoesDetailsDTOS;

    public Instant getStartDate() {
        return DataUtils.getStartOfDay_yyyy_MM_dd_HH_mm_ss(startDate);
    }

    public Instant getEndDate() {
        return DataUtils.getEndOfDay_yyyy_MM_dd_HH_mm_ss(endDate);
    }
}
