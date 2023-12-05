package com.shoes.service.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSDsResponse {

    private List<Long> sizeIds;
    private Long brandId;
    private BigDecimal startPrice;
    private BigDecimal endPrice;
}
