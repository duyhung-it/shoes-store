package com.shoes.service;

import com.shoes.service.dto.ShoesDetailsDTO;
import java.math.BigDecimal;
import java.util.List;

public interface DashboardService {
    Integer getOrderNumberInWeek();
    BigDecimal getRevenueInWeek();
    Integer getTotalCustomer();
    BigDecimal getRevenueOnShop();
    BigDecimal getRevenueOnline();
    List<BigDecimal> getRevenueInYear(Integer on);
    List<ShoesDetailsDTO> getAllBestSelling();
}
