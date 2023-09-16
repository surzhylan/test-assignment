package kz.shakhuali.springproject.testassignment.service.impl;

import kz.shakhuali.springproject.testassignment.dto.TransactionDto;
import kz.shakhuali.springproject.testassignment.mapper.TransactionMapper;
import kz.shakhuali.springproject.testassignment.model.Transaction;
import kz.shakhuali.springproject.testassignment.repository.TransactionRepository;
import kz.shakhuali.springproject.testassignment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public TransactionDto saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = TransactionMapper.MAPPER.toEntity(transactionDto);
        transaction.setDateTime(LocalDateTime.now());
        transactionRepository.save(transaction);
        return TransactionMapper.MAPPER.toDto(transaction);
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        return TransactionMapper.MAPPER.toDtoList(transactionRepository.findAll());
    }
}
