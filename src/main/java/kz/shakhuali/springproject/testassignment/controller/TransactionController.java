package kz.shakhuali.springproject.testassignment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kz.shakhuali.springproject.testassignment.dto.ExceededLimitTransactionDto;
import kz.shakhuali.springproject.testassignment.dto.TransactionDto;
import kz.shakhuali.springproject.testassignment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
@Tag(name = "Transactions", description = "API for managing transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    @Operation(summary = "Get all transactions", description = "Gets a list of all transactions")
    public List<TransactionDto> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @PostMapping
    @Operation(summary = "Create a new transaction", description = "Creates a new transaction")
    public TransactionDto createTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionService.saveTransaction(transactionDto);
    }

    @GetMapping("/exceeded-limit")
    @Operation(summary = "Get exceeded limit transactions",
            description = "Gets a list of transactions that exceeded the monthly limit")
    public List<ExceededLimitTransactionDto> getExceededLimitTransactions() {
        return transactionService.getExceededLimitTransactions();
    }
}
