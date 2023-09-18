package kz.shakhuali.springproject.testassignment.controller;

import kz.shakhuali.springproject.testassignment.model.ExchangeRate;
import kz.shakhuali.springproject.testassignment.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exchange-rates")
@RequiredArgsConstructor
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping("/fetch-and-save")
    public ResponseEntity<String> fetchAndSaveExchangeRates() {
        try {
            exchangeRateService.fetchAndSaveExchangeRates();
            return ResponseEntity.ok("Exchange rates fetched and saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch and save exchange rates.");
        }
    }

    @GetMapping("/all")
    public List<ExchangeRate> getAllExchangeRates() {
        return exchangeRateService.getAllExchangeRates();
    }
}