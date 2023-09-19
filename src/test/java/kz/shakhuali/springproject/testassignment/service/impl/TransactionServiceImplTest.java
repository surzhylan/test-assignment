package kz.shakhuali.springproject.testassignment.service.impl;

import kz.shakhuali.springproject.testassignment.dto.ExceededLimitTransactionDto;
import kz.shakhuali.springproject.testassignment.dto.TransactionDto;
import kz.shakhuali.springproject.testassignment.model.MonthlyLimit;
import kz.shakhuali.springproject.testassignment.model.Transaction;
import kz.shakhuali.springproject.testassignment.repository.TransactionRepository;
import kz.shakhuali.springproject.testassignment.service.MonthlyLimitService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private MonthlyLimitService monthlyLimitService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void itShouldSaveTransaction() {
        // Given
        MonthlyLimit monthlyLimit = MonthlyLimit.builder()
                .id(1L)
                .category("TestCategory")
                .amountLimit(BigDecimal.valueOf(1000))
                .timestamp(ZonedDateTime.now())
                .build();

        TransactionDto transactionDto = TransactionDto.builder()
                .currency("USD")
                .amount(BigDecimal.valueOf(500))
                .monthlyLimit(monthlyLimit.getId())
                .build();

        // When
        Mockito.when(monthlyLimitService.findById(monthlyLimit.getId())).thenReturn(monthlyLimit);
        TransactionDto savedTransactionDto = transactionService.saveTransaction(transactionDto);

        // Then
        Assertions.assertThat(savedTransactionDto)
                .isEqualToIgnoringGivenFields(transactionDto,"dateTime");
    }

    @Test
    void itShouldGetAllTransactions() {
        // Given
        MonthlyLimit monthlyLimit = MonthlyLimit.builder()
                .id(1L)
                .category("TestCategory")
                .amountLimit(BigDecimal.valueOf(1000))
                .timestamp(ZonedDateTime.now())
                .build();

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1L, 0000000123L, 9999999999L,
                "USD", new BigDecimal("100.00"), LocalDateTime.now(), false, monthlyLimit));
        transactions.add(new Transaction(2L, 0000000123L, 9999999999L,
                "KZT", new BigDecimal("200.00"), LocalDateTime.now(), false, monthlyLimit));
        transactions.add(new Transaction(3L, 0000000123L, 9999999999L,
                "RUB", new BigDecimal("300.00"), LocalDateTime.now(), false, monthlyLimit));

        // When
        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);
        List<TransactionDto> transactionDtos = transactionService.getAllTransactions();

        // Then
        Assertions.assertThat(transactionDtos).hasSize(3);
    }

    @Test
    void itShouldGetExceededLimitTransactions() {
        // Given
        MonthlyLimit monthlyLimit = MonthlyLimit.builder()
                .id(1L)
                .category("TestCategory")
                .amountLimit(BigDecimal.valueOf(1000))
                .timestamp(ZonedDateTime.now())
                .build();

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1L, 0000000123L, 9999999999L,
                "USD", new BigDecimal("500.00"), LocalDateTime.now(), true, monthlyLimit));
        transactions.add(new Transaction(2L, 0000000123L, 9999999999L,
                "USD", new BigDecimal("200.00"), LocalDateTime.now(), false, monthlyLimit));
        transactions.add(new Transaction(3L, 0000000123L, 9999999999L,
                "USD", new BigDecimal("300.00"), LocalDateTime.now(), true, monthlyLimit));

        // When
        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);
        List<ExceededLimitTransactionDto> exceededLimitTransactionDtos = transactionService.getExceededLimitTransactions();

        // Then
        Assertions.assertThat(exceededLimitTransactionDtos).hasSize(2);
    }
}