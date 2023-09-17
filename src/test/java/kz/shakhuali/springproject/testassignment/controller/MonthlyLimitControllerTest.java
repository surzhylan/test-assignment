package kz.shakhuali.springproject.testassignment.controller;

import kz.shakhuali.springproject.testassignment.service.MonthlyLimitService;
import org.junit.jupiter.api.BeforeEach;
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

import java.math.BigDecimal;
import java.time.YearMonth;

@WebMvcTest(controllers = MonthlyLimitController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MonthlyLimitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonthlyLimitService monthlyLimitService;

    private String category;
    private BigDecimal amountLimit;
    private int year;
    private int month;

    @BeforeEach
    void setUp() {
        category = "Groceries";
        amountLimit = new BigDecimal("500.00");
        year = 2023;
        month = 9;
    }

    @Test
    void itShouldSetMonthlyLimitSuccessfully() throws Exception {
        // When
        monthlyLimitService.setMonthlyLimit(category, amountLimit, YearMonth.of(year, month));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/limit")
                        .param("category", category)
                        .param("amount_limit", amountLimit.toString())
                        .param("year", String.valueOf(year))
                        .param("month", String.valueOf(month))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Monthly limit set successfully"));
    }

    @Test
    void itShouldHandleSetMonthlyLimitFailure() throws Exception {
        // Given
        String message = "Some errors occurred during the saving process";

        // When
        Mockito.doThrow(new RuntimeException(message))
                .when(monthlyLimitService)
                .setMonthlyLimit(category, amountLimit, YearMonth.of(year, month));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/limit")
                        .param("category", category)
                        .param("amount_limit", amountLimit.toString())
                        .param("year", String.valueOf(year))
                        .param("month", String.valueOf(month))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(message));
    }

    @Test
    void itShouldGetCurrentMonthlyLimitSuccessfully() throws Exception {
        // When
        Mockito.when(monthlyLimitService.getCurrentLimit(category, YearMonth.of(year, month)))
                .thenReturn(amountLimit);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/limit")
                        .param("category", category)
                        .param("year", String.valueOf(year))
                        .param("month", String.valueOf(month))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("500.00"));
    }

    @Test
    void itShouldHandleGetCurrentMonthlyLimitFailure() throws Exception {
        // When
        Mockito.doThrow(new RuntimeException("Some error occurred"))
                .when(monthlyLimitService).getCurrentLimit(category, YearMonth.of(year, month));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/limit")
                        .param("category", category)
                        .param("year", String.valueOf(year))
                        .param("month", String.valueOf(month))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
}