package kz.shakhuali.springproject.testassignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.closeTo;

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
        transactions.add(new TransactionDto(1L, "USD", new BigDecimal("100.00"), LocalDateTime.now()));
        transactions.add(new TransactionDto(2L, "EUR", new BigDecimal("200.00"), LocalDateTime.now()));
        transactions.add(new TransactionDto(3L, "GBP", new BigDecimal("300.00"), LocalDateTime.now()));

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
        TransactionDto transactionDto = new TransactionDto(null, "USD", new BigDecimal("500.00"), null);

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
}