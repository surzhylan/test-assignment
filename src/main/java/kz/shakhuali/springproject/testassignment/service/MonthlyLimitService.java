package kz.shakhuali.springproject.testassignment.service;

import kz.shakhuali.springproject.testassignment.model.MonthlyLimit;

import java.math.BigDecimal;
import java.time.YearMonth;

public interface MonthlyLimitService {

    void setMonthlyLimit(String category, BigDecimal amountLimit);

    BigDecimal getCurrentLimit(String category, YearMonth month);

    MonthlyLimit findById(Long id);
}
