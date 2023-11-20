package com.shoes.web.rest;

import com.shoes.config.Constants;
import com.shoes.service.DashboardService;
import com.shoes.service.dto.OrderDetailsDTO;
import com.shoes.service.dto.ShoesDetailsDTO;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashboard")
public class DashBoardResource {

    private final DashboardService dashboardService;

    @GetMapping("/revenue-year/on")
    public ResponseEntity<List<BigDecimal>> getRevenueOnYear() {
        return ResponseEntity.ok(dashboardService.getRevenueInYear(Constants.PAID_METHOD.ON));
    }

    @GetMapping("/revenue-year/off")
    public ResponseEntity<List<BigDecimal>> getRevenueOnYearOff() {
        return ResponseEntity.ok(dashboardService.getRevenueInYear(Constants.PAID_METHOD.OFF));
    }

    @GetMapping("/order-number")
    public ResponseEntity<Integer> getOrderNumber() {
        return ResponseEntity.ok(dashboardService.getOrderNumberInWeek());
    }

    @GetMapping("/order-revenue")
    public ResponseEntity<BigDecimal> getRevenueOnWeek() {
        return ResponseEntity.ok(dashboardService.getRevenueInWeek());
    }

    @GetMapping("/customers")
    public ResponseEntity<Integer> getCustomers() {
        return ResponseEntity.ok(dashboardService.getTotalCustomer());
    }

    @GetMapping("/order-revenue-on")
    public ResponseEntity<BigDecimal> getRevenueOn() {
        return ResponseEntity.ok(dashboardService.getRevenueOnline());
    }

    @GetMapping("/order-revenue-off")
    public ResponseEntity<BigDecimal> getRevenueOff() {
        return ResponseEntity.ok(dashboardService.getRevenueOnShop());
    }

    @GetMapping("/best-selling")
    public ResponseEntity<List<ShoesDetailsDTO>> getBestSelling() {
        return ResponseEntity.ok(dashboardService.getAllBestSelling());
    }
}
