package com.shoes.service.dto;

import java.math.BigDecimal;
import java.util.List;

public interface ShopShoesDTO {
    Integer getId();
    String getName();
    Integer getQuantity();
    BigDecimal getPrice();
    Integer getRating();
    Integer getShoes_id();
    Integer getBrand_id();
    Integer getColor_id();
    Integer getSize_id();
    String getDescription();
    String getPath();
    String getPaths();
    String getSizes();
    String getColor_ids();
    String getSize_ids();
    String getSize_names();
    String getColor_names();
}
