package kz.shakhuali.springproject.testassignment.mapper;

import kz.shakhuali.springproject.testassignment.dto.TransactionDto;
import kz.shakhuali.springproject.testassignment.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionMapper MAPPER = Mappers.getMapper(TransactionMapper.class);

    @Mapping(target = "monthlyLimit", source = "transaction.monthlyLimit.id")
    TransactionDto toDto(Transaction transaction);

    @Mapping(target = "monthlyLimit", ignore = true)
    Transaction toEntity(TransactionDto transactionDto);

    @Mapping(target = "monthlyLimit", source = "transaction.monthlyLimit.id")
    List<TransactionDto> toDtoList(List<Transaction> transactions);
}
