package kz.shakhuali.springproject.testassignment.service.impl;

import kz.shakhuali.springproject.testassignment.dto.ExceededLimitTransactionDto;
import kz.shakhuali.springproject.testassignment.dto.TransactionDto;
import kz.shakhuali.springproject.testassignment.mapper.TransactionMapper;
import kz.shakhuali.springproject.testassignment.model.MonthlyLimit;
import kz.shakhuali.springproject.testassignment.model.Transaction;
import kz.shakhuali.springproject.testassignment.repository.TransactionRepository;
import kz.shakhuali.springproject.testassignment.service.MonthlyLimitService;
import kz.shakhuali.springproject.testassignment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final MonthlyLimitService monthlyLimitService;

    @Override
    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        MonthlyLimit monthlyLimit = monthlyLimitService.findById(transactionDto.getMonthlyLimit());
        Transaction transaction = TransactionMapper.MAPPER.toEntity(transactionDto);
        transaction.setDateTime(LocalDateTime.now());
        transaction.setMonthlyLimit(monthlyLimit);

        checkAndSetLimitExceeded(transaction);

        transactionRepository.save(transaction);
        return TransactionMapper.MAPPER.toDto(transaction);
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        return TransactionMapper.MAPPER.toDtoList(transactionRepository.findAll());
    }

    @Override
    public List<ExceededLimitTransactionDto> getExceededLimitTransactions() {
        List<ExceededLimitTransactionDto> exceededLimitTransactions = new ArrayList<>();

        List<Transaction> transactions = transactionRepository.findAll();
        for(Transaction transaction : transactions) {
            if(transaction.isLimitExceeded()) {
                ExceededLimitTransactionDto exceededLimitTransactionDto = ExceededLimitTransactionDto.builder()
                        .id(transaction.getId())
                        .currency(transaction.getCurrency())
                        .amount(transaction.getAmount())
                        .dateTime(transaction.getDateTime())
                        .category(transaction.getMonthlyLimit().getCategory())
                        .limitSum(transaction.getMonthlyLimit().getAmountLimit())
                        .limitDatetime(transaction.getMonthlyLimit().getTimestamp())
                        .limitCurrencyShortname("USD")
                        .build();

                exceededLimitTransactions.add(exceededLimitTransactionDto);
            }
        }
        return exceededLimitTransactions;
    }

    private void checkAndSetLimitExceeded(Transaction transaction) {
        String category = transaction.getMonthlyLimit().getCategory();
        ZonedDateTime transactionTimestamp = transaction.getMonthlyLimit().getTimestamp();

        BigDecimal currentLimit = monthlyLimitService.getCurrentLimit(category, transactionTimestamp);

        ZonedDateTime startOfMonth = transactionTimestamp.withDayOfMonth(1).truncatedTo(ChronoUnit.SECONDS);
        ZonedDateTime endOfMonth = transactionTimestamp.with(TemporalAdjusters.lastDayOfMonth())
                .with(LocalTime.MAX).truncatedTo(ChronoUnit.SECONDS);

        BigDecimal totalAmountForMonth = transactionRepository.calculateTotalAmountForMonth(
                category,
                startOfMonth.toLocalDateTime(),
                endOfMonth.toLocalDateTime()
        );

        if (totalAmountForMonth == null) {
            totalAmountForMonth = BigDecimal.ZERO;
        } else {
            totalAmountForMonth = totalAmountForMonth.add(transaction.getAmount());

            if (totalAmountForMonth.compareTo(currentLimit) > 0) {
                transaction.setLimitExceeded(true);
            } else {
                transaction.setLimitExceeded(false);
            }
        }
    }
}
