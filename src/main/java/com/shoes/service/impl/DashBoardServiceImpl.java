package com.shoes.service.impl;

import com.shoes.repository.OrderRepository;
import com.shoes.repository.ShoesDetailsRepository;
import com.shoes.service.DashboardService;
import com.shoes.service.dto.RevenueDTO;
import com.shoes.service.dto.ShoesDetailsDTO;
import com.shoes.service.mapper.ShoesDetailsMapper;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashboardService {

    private final OrderRepository orderRepository;
    private final ShoesDetailsRepository shoesDetailsRepository;
    private final ShoesDetailsMapper shoesDetailsMapper;

    @Override
    public Integer getOrderNumberInWeek() {
        return orderRepository.getOrderNumberInWeek();
    }

    @Override
    public BigDecimal getRevenueInWeek() {
        return orderRepository.getRevenueInWeek();
    }

    @Override
    public Integer getTotalCustomer() {
        return orderRepository.getTotalCustomer();
    }

    @Override
    public BigDecimal getRevenueOnShop() {
        return orderRepository.getRevenueOnShop();
    }

    @Override
    public BigDecimal getRevenueOnline() {
        return orderRepository.getRevenueOnline();
    }

    @Override
    public List<BigDecimal> getRevenueInYear(Integer on) {
        List<BigDecimal> listAr = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            listAr.add(new BigDecimal(0));
        }
        List<RevenueDTO> list = orderRepository.getRevenueInYear(on);
        for (RevenueDTO revenueDTO : list) {
            listAr.set(revenueDTO.getMonth() - 1, revenueDTO.getValue());
        }
        return listAr;
    }

    @Override
    public List<ShoesDetailsDTO> getAllBestSelling() {
        return shoesDetailsMapper.toDto(shoesDetailsRepository.getTopBestSelling());
    }
}
