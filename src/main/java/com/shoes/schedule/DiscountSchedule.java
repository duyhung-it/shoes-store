package com.shoes.schedule;

import com.shoes.domain.Discount;
import com.shoes.repository.DiscountRepository;
import com.shoes.repository.DiscountShoesDetailsRepository;
import com.shoes.service.DiscountService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiscountSchedule {

    private final DiscountRepository discountRepository;
    private final DiscountService discountService;
    private final DiscountShoesDetailsRepository discountShoesDetailsRepository;

    @Scheduled(fixedDelayString = "${schedule.interval_time:1000)}")
    public void scanDiscount() {
        log.info("Bắt đầu chạy scan discount: ");
        discountService.scanDiscount();
    }
}
