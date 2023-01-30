package pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.mapper;

import org.mapstruct.Mapper;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.FinancialTransactionDTO;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository.FinancialTransaction;

@Mapper(componentModel = "spring")
public interface FinancialTransactionModelMapper {
    FinancialTransactionDTO mapFinancialTransactionEntityToFinancialTransactionDTO(FinancialTransaction transaction);
}
