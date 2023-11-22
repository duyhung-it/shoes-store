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
    List<Long> getSize();
    List<String> getPaths();
}
