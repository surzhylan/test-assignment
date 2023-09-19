package kz.shakhuali.springproject.testassignment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Exchange Rates", description = "API for managing exchange rates")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    @GetMapping("/fetch-and-save")
    @Operation(summary = "Fetch and save exchange rates",
            description = "Fetches and saves exchange rates from an external API")
    public ResponseEntity<String> fetchAndSaveExchangeRates() {
        try {
            exchangeRateService.fetchAndSaveExchangeRates();
            return ResponseEntity.ok("Exchange rates fetched and saved successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to fetch and save exchange rates.");
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all exchange rates", description = "Gets a list of all exchange rates")
    public List<ExchangeRate> getAllExchangeRates() {
        return exchangeRateService.getAllExchangeRates();
    }
}