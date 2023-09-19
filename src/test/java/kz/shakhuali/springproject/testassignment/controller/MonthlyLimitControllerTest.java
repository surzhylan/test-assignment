package kz.shakhuali.springproject.testassignment.controller;

import kz.shakhuali.springproject.testassignment.model.MonthlyLimit;
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
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private ZonedDateTime timestamp;

    @BeforeEach
    void setUp() {
        category = "Groceries";
        amountLimit = new BigDecimal("500.00");
        timestamp = ZonedDateTime.now();
    }

    @Test
    void itShouldSetMonthlyLimitSuccessfully() throws Exception {
        // When
        monthlyLimitService.setMonthlyLimit(category, amountLimit);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/limit")
                        .param("category", category)
                        .param("amount_limit", amountLimit.toString())
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
                .setMonthlyLimit(category, amountLimit);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/limit")
                        .param("category", category)
                        .param("amount_limit", amountLimit.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(message));
    }

    @Test
    void itShouldGetCurrentMonthlyLimitSuccessfully() throws Exception {
        // When
        Mockito.when(monthlyLimitService.getCurrentLimit(category, timestamp))
                .thenReturn(amountLimit);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/limit")
                        .param("category", category)
                        .param("timestamp", String.valueOf(timestamp))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("500.00"));
    }

    @Test
    void itShouldHandleGetCurrentMonthlyLimitFailure() throws Exception {
        // When
        Mockito.doThrow(new RuntimeException("Some error occurred"))
                .when(monthlyLimitService).getCurrentLimit(category, timestamp);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/limit")
                        .param("category", category)
                        .param("timestamp", String.valueOf(timestamp))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void itShouldGetAllTransactions() throws Exception {
        // Given
        List<MonthlyLimit> monthlyLimits = new ArrayList<>();
        monthlyLimits.add(new MonthlyLimit(1L, category, amountLimit, ZonedDateTime.now()));
        monthlyLimits.add(new MonthlyLimit(2L, "Entertainment",
                new BigDecimal("600.00"), ZonedDateTime.now()));
        monthlyLimits.add(new MonthlyLimit(3L, "Travel",
                new BigDecimal("700.00"), ZonedDateTime.now()));

        // When
        Mockito.when(monthlyLimitService.getAllLimits()).thenReturn(monthlyLimits);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/limit/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(monthlyLimits.size()));
    }

    @Test
    void itShouldGetAllTransactionsFailure() throws Exception {
        // When
        Mockito.doThrow(new RuntimeException("Some error occurred"))
                .when(monthlyLimitService).getAllLimits();

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/limit/all"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
}