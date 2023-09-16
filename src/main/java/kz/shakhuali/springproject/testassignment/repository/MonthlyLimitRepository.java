package kz.shakhuali.springproject.testassignment.repository;

import kz.shakhuali.springproject.testassignment.model.MonthlyLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;

@Repository
public interface MonthlyLimitRepository extends JpaRepository<MonthlyLimit, Long> {

    MonthlyLimit findByCategoryAndMonth(String category, YearMonth month);
}
