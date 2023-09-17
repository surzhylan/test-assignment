package kz.shakhuali.springproject.testassignment.service.impl;

import kz.shakhuali.springproject.testassignment.model.MonthlyLimit;
import kz.shakhuali.springproject.testassignment.repository.MonthlyLimitRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.YearMonth;

import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(MockitoExtension.class)
class MonthlyLimitServiceImplTest {

    @Mock
    private MonthlyLimitRepository monthlyLimitRepository;

    @InjectMocks
    private MonthlyLimitServiceImpl monthlyLimitService;

    @Test
    void itShouldSetMonthlyLimitWhenLimitExists() {
        // Given
        String category = "Groceries";
        BigDecimal amountLimit = new BigDecimal("500.00");
        YearMonth month = YearMonth.of(2023, 9);

        MonthlyLimit existingLimit = new MonthlyLimit(1L, category, new BigDecimal("300.00"), month);

        // When
        Mockito.when(monthlyLimitRepository.findByCategoryAndMonth(category, month)).thenReturn(existingLimit);
        monthlyLimitService.setMonthlyLimit(category, amountLimit, month);

        // Then
        Assertions.assertThat(existingLimit.getAmountLimit()).isEqualTo(amountLimit);
    }

    @Test
    void itShouldSetMonthlyLimitWhenLimitDoesNotExist() {
        // Given
        String category = "Entertainment";
        BigDecimal amountLimit = new BigDecimal("200.00");
        YearMonth month = YearMonth.of(2023, 9);

        // When
        Mockito.when(monthlyLimitRepository.findByCategoryAndMonth(category, month)).thenReturn(null);
        monthlyLimitService.setMonthlyLimit(category, amountLimit, month);

        // Then
        Mockito.verify(monthlyLimitRepository, Mockito.times(1)).save(argThat(monthlyLimit ->
                monthlyLimit.getCategory().equals(category) &&
                        monthlyLimit.getAmountLimit().equals(amountLimit) &&
                        monthlyLimit.getMonth().equals(month)));
    }

    @Test
    void itShouldGetCurrentLimit() {
        // Given
        String category = "Groceries";
        YearMonth month = YearMonth.of(2023, 9);
        BigDecimal expectedLimit = new BigDecimal("500.00");

        MonthlyLimit currentLimit = new MonthlyLimit(1L, category, expectedLimit, month);

        // When
        Mockito.when(monthlyLimitRepository.findByCategoryAndMonth(category, month)).thenReturn(currentLimit);
        BigDecimal result = monthlyLimitService.getCurrentLimit(category, month);

        // Then
        Assertions.assertThat(result).isEqualByComparingTo(expectedLimit);
    }
}