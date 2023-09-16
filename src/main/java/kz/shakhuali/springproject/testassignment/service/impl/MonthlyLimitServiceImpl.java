package kz.shakhuali.springproject.testassignment.service.impl;

import kz.shakhuali.springproject.testassignment.model.MonthlyLimit;
import kz.shakhuali.springproject.testassignment.repository.MonthlyLimitRepository;
import kz.shakhuali.springproject.testassignment.service.MonthlyLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class MonthlyLimitServiceImpl implements MonthlyLimitService {

    private final MonthlyLimitRepository monthlyLimitRepository;

    @Override
    public void setMonthlyLimit(String category, BigDecimal amountLimit, YearMonth month) {
        MonthlyLimit limitExists = monthlyLimitRepository.findByCategoryAndMonth(category, month);

        if(limitExists != null) {
            limitExists.setAmountLimit(amountLimit);
            monthlyLimitRepository.save(limitExists);
        } else {
            MonthlyLimit monthlyLimit = MonthlyLimit.builder()
                    .category(category)
                    .amountLimit(amountLimit != null ? amountLimit : BigDecimal.valueOf(1000))
                    .month(month)
                    .build();

            monthlyLimitRepository.save(monthlyLimit);
        }
    }

    @Override
    public BigDecimal getCurrentLimit(String category, YearMonth month) {
        MonthlyLimit currentLimit = monthlyLimitRepository.findByCategoryAndMonth(category, month);
        return currentLimit.getAmountLimit();
    }
}
