package kz.shakhuali.springproject.testassignment.mapper;

import kz.shakhuali.springproject.testassignment.dto.TransactionDto;
import kz.shakhuali.springproject.testassignment.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper MAPPER = Mappers.getMapper(TransactionMapper.class);

    TransactionDto toDto(Transaction transaction);

    Transaction toEntity(TransactionDto transactionDto);

    List<TransactionDto> toDtoList(List<Transaction> transactions);
}
