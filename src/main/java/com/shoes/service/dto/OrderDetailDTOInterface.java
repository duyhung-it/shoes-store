package com.shoes.service.dto;

import java.math.BigDecimal;

public interface OrderDetailDTOInterface {
    Integer getId();

    Integer getIdsh();
    Integer getIdsz();
    Integer getIdc();
    Integer getIdb();

    Integer getQuantity();

    Integer getQuantityShoesDetail();

    Integer getStatus();

    Integer getShoesdetailid();

    String getPath();

    BigDecimal getPriceShoes();

    String getNameShoes();
    String getNameSize();
    String getNameColor();
    String getNameBrand();

    BigDecimal getDiscount();
}
