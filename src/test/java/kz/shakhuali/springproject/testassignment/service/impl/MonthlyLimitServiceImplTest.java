package kz.shakhuali.springproject.testassignment.service.impl;

import kz.shakhuali.springproject.testassignment.model.MonthlyLimit;
import kz.shakhuali.springproject.testassignment.repository.MonthlyLimitRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;

@ExtendWith(MockitoExtension.class)
class MonthlyLimitServiceImplTest {

    @Mock
    private MonthlyLimitRepository monthlyLimitRepository;

    @InjectMocks
    private MonthlyLimitServiceImpl monthlyLimitService;

    private String category;
    private BigDecimal amountLimit;

    @BeforeEach
    void setUp() {
        category = "Groceries";
        amountLimit = new BigDecimal("500.00");
    }

    @Test
    void itShouldSetMonthlyLimitWhenLimitExists() {
        // Given
        MonthlyLimit existingLimit = MonthlyLimit.builder()
                .id(1L)
                .category(category)
                .amountLimit(new BigDecimal("300.00"))
                .timestamp(ZonedDateTime.now())
                .build();

        // When
        Mockito.when(monthlyLimitRepository.findByCategoryAndTimestamp(
                        Mockito.eq(category), Mockito.any(ZonedDateTime.class)))
                .thenReturn(existingLimit);

        // Then
        Assertions.assertThatThrownBy(() -> monthlyLimitService.setMonthlyLimit(category, amountLimit))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Updating existing limits is not allowed");
    }

    @Test
    void itShouldSetMonthlyLimitWhenLimitDoesNotExist() {
        // Given
        BigDecimal amountLimit = new BigDecimal("200.00");

        // When
        Mockito.when(monthlyLimitRepository.findByCategoryAndTimestamp(
                        Mockito.eq(category), Mockito.any(ZonedDateTime.class)))
                .thenReturn(null);
        monthlyLimitService.setMonthlyLimit(category, amountLimit);

        // Then
        Mockito.verify(monthlyLimitRepository, Mockito.times(1)).save(argThat(monthlyLimit ->
                monthlyLimit.getCategory().equals(category) &&
                        monthlyLimit.getAmountLimit().equals(amountLimit)));
    }

    @Test
    void itShouldGetCurrentLimit() {
        // Given
        MonthlyLimit currentLimit = MonthlyLimit.builder()
                .id(1L)
                .category(category)
                .amountLimit(amountLimit)
                .timestamp(ZonedDateTime.now())
                .build();

        // When
        Mockito.when(monthlyLimitRepository.findByCategoryAndTimestamp(category, currentLimit.getTimestamp()))
                .thenReturn(currentLimit);
        BigDecimal result = monthlyLimitService.getCurrentLimit(category, currentLimit.getTimestamp());

        // Then
        Assertions.assertThat(result).isEqualByComparingTo(amountLimit);
    }

    @Test
    void itShouldFindMonthlyLimitById() {
        // Given
        Long id = 1L;

        MonthlyLimit expectedLimit = new MonthlyLimit(id, category, amountLimit, ZonedDateTime.now());

        // When
        Mockito.when(monthlyLimitRepository.findById(id)).thenReturn(Optional.of(expectedLimit));
        MonthlyLimit result = monthlyLimitService.findById(id);

        // Then
        Assertions.assertThat(result).isEqualTo(expectedLimit);
    }

    @Test
    void itShouldReturnNullIfMonthlyLimitNotFound() {
        // Given
        Long id = 1L;

        // When
        Mockito.when(monthlyLimitRepository.findById(id)).thenReturn(Optional.empty());
        MonthlyLimit result = monthlyLimitService.findById(id);

        // Then
        Assertions.assertThat(result).isNull();
    }

    @Test
    void itShouldGetAllLimits() {
        // Given
        List<MonthlyLimit> monthlyLimits = new ArrayList<>();
        monthlyLimits.add(new MonthlyLimit(1L, category, amountLimit, ZonedDateTime.now()));
        monthlyLimits.add(new MonthlyLimit(2L, "Entertainment",
                new BigDecimal("600.00"), ZonedDateTime.now()));
        monthlyLimits.add(new MonthlyLimit(3L, "Travel",
                new BigDecimal("700.00"), ZonedDateTime.now()));

        // When
        Mockito.when(monthlyLimitRepository.findAll()).thenReturn(monthlyLimits);
        List<MonthlyLimit> result = monthlyLimitService.getAllLimits();

        // Then
        Assertions.assertThat(result).hasSize(3);
    }
}