package kz.shakhuali.springproject.testassignment.service;

import java.math.BigDecimal;
import java.time.YearMonth;

public interface MonthlyLimitService {

    void setMonthlyLimit(String category, BigDecimal amountLimit, YearMonth month);

    BigDecimal getCurrentLimit(String category, YearMonth month);
}
