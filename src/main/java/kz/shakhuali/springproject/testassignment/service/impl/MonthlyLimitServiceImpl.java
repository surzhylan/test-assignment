package kz.shakhuali.springproject.testassignment.service.impl;

import kz.shakhuali.springproject.testassignment.model.MonthlyLimit;
import kz.shakhuali.springproject.testassignment.repository.MonthlyLimitRepository;
import kz.shakhuali.springproject.testassignment.service.MonthlyLimitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MonthlyLimitServiceImpl implements MonthlyLimitService {

    private final MonthlyLimitRepository monthlyLimitRepository;

    @Override
    public void setMonthlyLimit(String category, BigDecimal amountLimit) {
        ZonedDateTime timestamp = ZonedDateTime.now();
        MonthlyLimit limitExists = monthlyLimitRepository.findByCategoryAndTimestamp(category, timestamp);

        if (limitExists != null) {
            log.error("Updating existing limits is not allowed");
            throw new IllegalArgumentException("Updating existing limits is not allowed");
        } else {
            MonthlyLimit monthlyLimit = MonthlyLimit.builder()
                    .category(category)
                    .amountLimit(amountLimit != null ? amountLimit : BigDecimal.valueOf(1000))
                    .timestamp(timestamp)
                    .build();

            monthlyLimitRepository.save(monthlyLimit);
        }
    }

    @Override
    public BigDecimal getCurrentLimit(String category, ZonedDateTime timestamp) {
        MonthlyLimit currentLimit = monthlyLimitRepository.findByCategoryAndTimestamp(category, timestamp);
        return currentLimit.getAmountLimit();
    }


    @Override
    public MonthlyLimit findById(Long id) {
        Optional<MonthlyLimit> monthlyLimitOptional = monthlyLimitRepository.findById(id);
        return monthlyLimitOptional.orElse(null);
    }

    @Override
    public List<MonthlyLimit> getAllLimits() {
        return monthlyLimitRepository.findAll();
    }
}
