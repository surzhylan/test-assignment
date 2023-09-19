package kz.shakhuali.springproject.testassignment.service.impl;

import kz.shakhuali.springproject.testassignment.model.ExchangeRate;
import kz.shakhuali.springproject.testassignment.repository.ExchangeRateRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceImplTest {

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @Test
    void itShouldFetchAndSaveExchangeRates() {
        // Given
        Map<String, Object> responseBody = new HashMap<>();
        Map<String, Double> conversionRates = new HashMap<>();
        conversionRates.put("KZT", 450.0);
        conversionRates.put("RUB", 75.0);
        responseBody.put("conversion_rates", conversionRates);

        ResponseEntity<Map> responseEntity = ResponseEntity.ok(responseBody);
        String apiUrl = "https://v6.exchangerate-api.com/v6/cc9589065b33f00a736dfb09/latest/USD";

        // When
        Mockito.when(restTemplate.getForEntity(apiUrl, Map.class)).thenReturn(responseEntity);
        exchangeRateService.fetchAndSaveExchangeRates();

        // Then
        Assertions.assertThat(exchangeRateService.getAllExchangeRates())
                .isNotNull();
    }

    @Test
    void itShouldGetAllExchangeRates() {
        // Given
        List<ExchangeRate> expectedExchangeRates = new ArrayList<>();
        expectedExchangeRates.add(new ExchangeRate(1L, "USD", "KZT",
                450.0, LocalDate.now()));
        expectedExchangeRates.add(new ExchangeRate(2L, "USD", "RUB",
                75.0, LocalDate.now()));

        // When
        Mockito.when(exchangeRateRepository.findAll()).thenReturn(expectedExchangeRates);
        List<ExchangeRate> actualExchangeRates = exchangeRateService.getAllExchangeRates();

        // Then
        Assertions.assertThat(actualExchangeRates)
                .isNotNull()
                .hasSize(2);
    }
}