package kz.shakhuali.springproject.testassignment.controller;

import kz.shakhuali.springproject.testassignment.model.ExchangeRate;
import kz.shakhuali.springproject.testassignment.service.ExchangeRateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = ExchangeRateController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ExchangeRateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeRateService exchangeRateService;

    @Test
    void itShouldFetchAndSaveExchangeRates() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/exchange-rates/fetch-and-save"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Exchange rates fetched and saved successfully."));
    }

    @Test
    void itShouldGetAllExchangeRates() throws Exception {
        // Given
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        exchangeRates.add(new ExchangeRate(1L, "USD", "KZT", 450.0, LocalDate.now()));
        exchangeRates.add(new ExchangeRate(2L, "USD", "RUB", 75.0, LocalDate.now()));

        // When
        Mockito.when(exchangeRateService.getAllExchangeRates()).thenReturn(exchangeRates);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/exchange-rates/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(exchangeRates.size()));
    }
}