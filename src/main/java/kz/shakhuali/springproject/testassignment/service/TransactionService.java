package kz.shakhuali.springproject.testassignment.service;

import kz.shakhuali.springproject.testassignment.dto.TransactionDto;

import java.util.List;

public interface TransactionService {

    TransactionDto saveTransaction(TransactionDto transactionDto);

    List<TransactionDto> getAllTransactions();
}
