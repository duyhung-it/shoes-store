package com.shoes.service.dto;

import java.math.BigDecimal;

public interface ShoesVariant {
    Integer getId();
    String getName();
    String getCode();
    String getBrandName();
    String getShoesCode();
    Integer getQuantity();
    BigDecimal getPrice();
    BigDecimal getTax();
    BigDecimal getImport_price();
    Integer getStatus();
    Integer getRating();
    Integer getShoes_id();
    Integer getBrand_id();
    Integer getColor_id();
    String getColor_name();
    String getSize_name();
    Integer getSize_id();
    String getDescription();
    String getPath();
    String getPaths();
    String getSizes();
    String getColor_ids();
    String getSize_ids();
    String getSize_names();
    String getColor_names();
    String getDiscount_amount();
    String getDiscount_method();
    String getDiscount_name();
}
