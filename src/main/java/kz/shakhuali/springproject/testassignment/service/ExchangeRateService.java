package kz.shakhuali.springproject.testassignment.service;

import kz.shakhuali.springproject.testassignment.model.ExchangeRate;

import java.util.List;

public interface ExchangeRateService {

    void fetchAndSaveExchangeRates();

    List<ExchangeRate> getAllExchangeRates();
}
