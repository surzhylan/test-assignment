package kz.shakhuali.springproject.testassignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kz.shakhuali.springproject.testassignment.dto.ExceededLimitTransactionDto;
import kz.shakhuali.springproject.testassignment.dto.TransactionDto;
import kz.shakhuali.springproject.testassignment.service.TransactionService;
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
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.hasSize;

@WebMvcTest(controllers = TransactionController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void itShouldGetAllTransactions() throws Exception {
        // Given
        List<TransactionDto> transactions = new ArrayList<>();
        transactions.add(new TransactionDto(1L, 0000000123L, 9999999999L,
                "USD", new BigDecimal("100.00"), LocalDateTime.now(), 1L));
        transactions.add(new TransactionDto(2L, 0000000123L, 9999999999L,
                "EUR", new BigDecimal("200.00"), LocalDateTime.now(), 1L));
        transactions.add(new TransactionDto(3L, 0000000123L, 9999999999L,
                "GBP", new BigDecimal("300.00"), LocalDateTime.now(), 1L));

        // When
        Mockito.when(transactionService.getAllTransactions()).thenReturn(transactions);

        // Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transaction"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(transactions.size()));
    }

    @Test
    void itShouldCreateTransaction() throws Exception {
        // Given
        TransactionDto transactionDto = new TransactionDto(null, 0000000123L, 9999999999L,
                "USD", new BigDecimal("500.00"), null, 1L);

        // When
        Mockito.when(transactionService.saveTransaction(Mockito.any(TransactionDto.class)))
                .thenAnswer(invocation -> {
                    TransactionDto arg = invocation.getArgument(0);
                    arg.setId(1L);
                    return arg;
                });

        // Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("USD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount",
                        closeTo(transactionDto.getAmount().doubleValue(), 0.01)));
    }

    @Test
    void itShouldGetExceededLimitTransactions() throws Exception {
        // Given
        List<ExceededLimitTransactionDto> exceededLimitTransactions = new ArrayList<>();
        exceededLimitTransactions.add(
                ExceededLimitTransactionDto.builder()
                        .id(1L)
                        .currency("USD")
                        .amount(BigDecimal.valueOf(100.00))
                        .dateTime(LocalDateTime.now())
                        .category("Category1")
                        .limitSum(BigDecimal.valueOf(1000.00))
                        .limitDatetime(ZonedDateTime.now())
                        .limitCurrencyShortname("USD")
                        .build()
        );

        exceededLimitTransactions.add(
                ExceededLimitTransactionDto.builder()
                        .id(2L)
                        .currency("EUR")
                        .amount(BigDecimal.valueOf(200.00))
                        .dateTime(LocalDateTime.now())
                        .category("Category2")
                        .limitSum(BigDecimal.valueOf(2000.00))
                        .limitDatetime(ZonedDateTime.now())
                        .limitCurrencyShortname("EUR")
                        .build()
        );

        // When
        Mockito.when(transactionService.getExceededLimitTransactions()).thenReturn(exceededLimitTransactions);

        //Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/transaction/exceeded-limit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)));
    }
}