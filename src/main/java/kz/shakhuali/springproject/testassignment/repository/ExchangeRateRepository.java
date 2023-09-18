package kz.shakhuali.springproject.testassignment.repository;

import kz.shakhuali.springproject.testassignment.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

}
