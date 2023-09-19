package kz.shakhuali.springproject.testassignment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.shakhuali.springproject.testassignment.model.MonthlyLimit;
import kz.shakhuali.springproject.testassignment.service.MonthlyLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/limit")
@RequiredArgsConstructor
@Tag(name = "Monthly Limits", description = "API for managing monthly limits")
public class MonthlyLimitController {

    private final MonthlyLimitService monthlyLimitService;

    @PostMapping
    @Operation(summary = "Set a monthly limit",
            description = "Sets a monthly spending limit for a specific category")
    public ResponseEntity<String> setMonthlyLimit(@RequestParam String category,
                                                  @RequestParam("amount_limit") BigDecimal amountLimit) {
        try {
            monthlyLimitService.setMonthlyLimit(category, amountLimit);
            return ResponseEntity.ok("Monthly limit set successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Some errors occurred during the saving process");
        }
    }

    @GetMapping
    @Operation(summary = "Get the current monthly limit",
            description = "Gets the current monthly spending limit for a category")
    public ResponseEntity<BigDecimal> getCurrentMonthlyLimit(@RequestParam String category,
                                                             @RequestParam("timestamp") String timestamp) {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(timestamp);
            return ResponseEntity.ok(monthlyLimitService.getCurrentLimit(category, zonedDateTime));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all monthly limits", description = "Gets a list of all monthly spending limits")
    public ResponseEntity<List<MonthlyLimit>> getAllLimits() {
        try {
            return ResponseEntity.ok(monthlyLimitService.getAllLimits());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
