package kz.shakhuali.springproject.testassignment.service;

import kz.shakhuali.springproject.testassignment.model.MonthlyLimit;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

public interface MonthlyLimitService {

    void setMonthlyLimit(String category, BigDecimal amountLimit);

    BigDecimal getCurrentLimit(String category, ZonedDateTime time);

    MonthlyLimit findById(Long id);

    List<MonthlyLimit> getAllLimits();
}
