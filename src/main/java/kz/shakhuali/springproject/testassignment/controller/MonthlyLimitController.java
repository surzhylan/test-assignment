package kz.shakhuali.springproject.testassignment.controller;

import kz.shakhuali.springproject.testassignment.service.MonthlyLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.YearMonth;

@RestController
@RequestMapping("/api/v1/limit")
@RequiredArgsConstructor
public class MonthlyLimitController {

    private final MonthlyLimitService monthlyLimitService;

    @PostMapping
    public ResponseEntity<String> setMonthlyLimit(@RequestParam String category,
            @RequestParam("amount_limit") BigDecimal amountLimit,
            @RequestParam int year,
            @RequestParam int month
    ) {
        try {
            YearMonth yearMonth = YearMonth.of(year, month);
            monthlyLimitService.setMonthlyLimit(category, amountLimit, yearMonth);
            return ResponseEntity.ok("Monthly limit set successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Some errors occurred during the saving process");
        }
    }

    @GetMapping
    public ResponseEntity<BigDecimal> getCurrentMonthlyLimit(@RequestParam String category,
            @RequestParam int year,
            @RequestParam int month
    ) {
        try {
            YearMonth yearMonth = YearMonth.of(year, month);
            return ResponseEntity.ok(monthlyLimitService.getCurrentLimit(category, yearMonth));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
