package pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.api;

import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.CreateFinancialTransactionDTO;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.FinancialTransactionDTO;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.UpdateFinancialTransactionDTO;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface FinancialTransactionService {
    FinancialTransactionDTO createFinancialTransaction(@Valid CreateFinancialTransactionDTO createFinancialTransactionDTO);

    List<FinancialTransactionDTO> getFinancialTransactionsByWalletId(@Min(1) @NotNull Long walletId);

    FinancialTransactionDTO findById(@Min(1) @NotNull Long id);

    FinancialTransactionDTO updateTransaction(@Min(1) @NotNull Long id, @Valid UpdateFinancialTransactionDTO updateTransactionDTO);

    void deleteTransactionById(@Min(1) @NotNull Long id);
}
