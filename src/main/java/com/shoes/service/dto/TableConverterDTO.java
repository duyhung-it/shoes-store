package com.shoes.service.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableConverterDTO {

    private String name;
    private String size;
    private Integer quantity;
    private String price;
    private String totalPrice;
}
