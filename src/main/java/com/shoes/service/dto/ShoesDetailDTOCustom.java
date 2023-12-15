package com.shoes.service.dto;

import java.math.BigDecimal;

public interface ShoesDetailDTOCustom {

    String getName();
    String getPrice();
    Integer getIdsh();
    Integer getIdsz();
    Integer getIdc();
    Integer getIdb();
    String getPath();
    Integer getDiscountmethod();
    BigDecimal getDiscountamount_1_2();
    BigDecimal getDiscountamount_3_4();
}
