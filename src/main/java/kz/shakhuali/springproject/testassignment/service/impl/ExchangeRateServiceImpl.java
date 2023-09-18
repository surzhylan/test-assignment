package kz.shakhuali.springproject.testassignment.service.impl;

import kz.shakhuali.springproject.testassignment.model.ExchangeRate;
import kz.shakhuali.springproject.testassignment.repository.ExchangeRateRepository;
import kz.shakhuali.springproject.testassignment.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository exchangeRateRepository;
    private final RestTemplate restTemplate;

    @Override
    public void fetchAndSaveExchangeRates() {
        String apiUrl = "https://v6.exchangerate-api.com/v6/cc9589065b33f00a736dfb09/latest/USD";

        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(apiUrl, Map.class);
        Map<String, Object> response = responseEntity.getBody();

        if (response != null && response.containsKey("conversion_rates")) {
            Map<String, Double> conversionRates = (Map<String, Double>) response.get("conversion_rates");
            LocalDate currentDate = LocalDate.now();

            if (conversionRates.containsKey("KZT")) {
                Double kztUsdRate = conversionRates.get("KZT");
                saveExchangeRate("USD", "KZT", kztUsdRate, currentDate);
            }

            if (conversionRates.containsKey("RUB")) {
                Double rubUsdRate = conversionRates.get("RUB");
                saveExchangeRate("USD", "RUB", rubUsdRate, currentDate);
            }
        }
    }

    private void saveExchangeRate(String baseCurrency, String targetCurrency, Double rate, LocalDate date) {
        ExchangeRate exchangeRate = ExchangeRate.builder()
                .baseCurrency(baseCurrency)
                .targetCurrency(targetCurrency)
                .rate(rate)
                .date(date)
                .build();

        exchangeRateRepository.save(exchangeRate);
    }

    @Override
    public List<ExchangeRate> getAllExchangeRates() {
        return exchangeRateRepository.findAll();
    }
}
