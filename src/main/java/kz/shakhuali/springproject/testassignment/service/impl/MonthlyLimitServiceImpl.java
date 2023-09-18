package kz.shakhuali.springproject.testassignment.service.impl;

import kz.shakhuali.springproject.testassignment.model.MonthlyLimit;
import kz.shakhuali.springproject.testassignment.repository.MonthlyLimitRepository;
import kz.shakhuali.springproject.testassignment.service.MonthlyLimitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonthlyLimitServiceImpl implements MonthlyLimitService {

    private final MonthlyLimitRepository monthlyLimitRepository;

    @Override
    public void setMonthlyLimit(String category, BigDecimal amountLimit) {
        YearMonth month = YearMonth.now();
        MonthlyLimit limitExists = monthlyLimitRepository.findByCategoryAndMonth(category, month);

        if(limitExists != null) {
            log.error("Updating existing limits is not allowed");
            throw new IllegalArgumentException("Updating existing limits is not allowed");
        } else {
            MonthlyLimit monthlyLimit = MonthlyLimit.builder()
                    .category(category)
                    .amountLimit(amountLimit != null ? amountLimit : BigDecimal.valueOf(1000))
                    .month(month)
                    .timestamp(ZonedDateTime.now())
                    .build();

            monthlyLimitRepository.save(monthlyLimit);
        }
    }

    @Override
    public BigDecimal getCurrentLimit(String category, YearMonth month) {
        MonthlyLimit currentLimit = monthlyLimitRepository.findByCategoryAndMonth(category, month);
        return currentLimit.getAmountLimit();
    }

    @Override
    public MonthlyLimit findById(Long id) {
        return monthlyLimitRepository.findById(id).orElse(null);
    }
}
