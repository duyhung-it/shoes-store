package com.shoes.service.dto;

import com.shoes.domain.ShoesDetails;

import java.math.BigDecimal;

public interface CartDetailDTO {

    Integer getId();

    Integer getQuantity();

    Integer getQuantityShoesDetail();

    Integer getStatus();

    Integer getShoesdetailid();

    String getPath();

    BigDecimal getPrice();

    String getNameshoes();
    String getNamesize();
    String getNamecolor();

}
