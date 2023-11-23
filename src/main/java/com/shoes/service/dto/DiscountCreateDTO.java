package com.shoes.service.dto;

import com.shoes.util.DataUtils;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotBlank(message = "{error.discount.name.not.blank}")
    private String name;

    @NotNull(message = "{error.discount.startDate.not.null}")
    private String startDate;

    @NotNull(message = "{error.discount.endDate.not.null}")
    private String endDate;

    @NotNull(message = "{error.discount.method.not.null}")
    private Integer discountMethod;

    private BigDecimal discountAmount;

    private Integer discountStatus;

    @NotEmpty(message = "{error.discount.details.not.empty}")
    private List<DiscountShoesDetailsDTO> discountShoesDetailsDTOS;

    public Instant getStartDate() {
        return DataUtils.getStartOfDay_yyyy_MM_dd_HH_mm_ss(startDate);
    }

    public Instant getEndDate() {
        return DataUtils.getEndOfDay_yyyy_MM_dd_HH_mm_ss(endDate);
    }
}
