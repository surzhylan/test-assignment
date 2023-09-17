package kz.shakhuali.springproject.testassignment.service.impl;

import kz.shakhuali.springproject.testassignment.dto.TransactionDto;
import kz.shakhuali.springproject.testassignment.model.Transaction;
import kz.shakhuali.springproject.testassignment.repository.TransactionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void itShouldSaveTransaction() {
        // Given
        TransactionDto transactionDto = TransactionDto.builder()
                .id(1L)
                .currency("USD")
                .amount(new BigDecimal("500.00"))
                .build();

        // When
        TransactionDto savedTransactionDto = transactionService.saveTransaction(transactionDto);

        // Then
        Assertions.assertThat(savedTransactionDto)
                .isEqualToIgnoringGivenFields(transactionDto,"dateTime");
    }

    @Test
    void itShouldGetAllTransactions() {
        // Given
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1L, "USD", new BigDecimal("100.00"), LocalDateTime.now()));
        transactions.add(new Transaction(2L, "KZT", new BigDecimal("200.00"), LocalDateTime.now()));
        transactions.add(new Transaction(3L, "RUB", new BigDecimal("300.00"), LocalDateTime.now()));

        // When
        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);
        List<TransactionDto> transactionDtos = transactionService.getAllTransactions();

        // Then
        Assertions.assertThat(transactionDtos).hasSize(3);
    }
}