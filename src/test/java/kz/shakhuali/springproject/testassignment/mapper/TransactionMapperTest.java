package kz.shakhuali.springproject.testassignment.mapper;

import kz.shakhuali.springproject.testassignment.dto.TransactionDto;
import kz.shakhuali.springproject.testassignment.model.Transaction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionMapperTest {

    private final TransactionMapper MAPPER = Mappers.getMapper(TransactionMapper.class);

    @Test
    void itShouldToDto() {
        // Given
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setCurrency("KZT");
        transaction.setDateTime(LocalDateTime.now());

        // When
        TransactionDto dto = MAPPER.toDto(transaction);

        // Then
        Assertions.assertThat(dto)
                .isNotNull().usingRecursiveComparison().isEqualTo(transaction);
    }

    @Test
    void itShouldToEntity() {
        // Given
        TransactionDto transactionDto = TransactionDto.builder()
                .id(1L)
                .currency("KZT")
                .amount(new BigDecimal("100.00"))
                .dateTime(LocalDateTime.now())
                .build();

        // When
        Transaction transaction = MAPPER.toEntity(transactionDto);

        // Then
        Assertions.assertThat(transaction)
                .isNotNull().usingRecursiveComparison().isEqualTo(transactionDto);
    }

    @Test
    void itShouldToDtoList() {
        // Given
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction(1L, "KZT", new BigDecimal("100.00"), LocalDateTime.now()));
        transactions.add(new Transaction(2L, "USD", new BigDecimal("50.00"), LocalDateTime.now()));
        transactions.add(new Transaction(3L, "RUB", new BigDecimal("75.00"), LocalDateTime.now()));

        // When
        List<TransactionDto> dtos = MAPPER.toDtoList(transactions);

        // Then
        Assertions.assertThat(dtos)
                .isNotNull()
                .hasSize(3);

        Assertions.assertThat(dtos.get(0))
                .isEqualToComparingFieldByField(transactions.get(0));

        Assertions.assertThat(dtos.get(1))
                .isEqualToComparingFieldByField(transactions.get(1));

        Assertions.assertThat(dtos.get(2))
                .isEqualToComparingFieldByField(transactions.get(2));
    }
}