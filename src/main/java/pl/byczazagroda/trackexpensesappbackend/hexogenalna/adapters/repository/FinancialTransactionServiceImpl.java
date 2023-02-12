package pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.CreateFinancialTransactionDTO;
import org.springframework.validation.annotation.Validated;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.FinancialTransactionDTO;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.UpdateFinancialTransactionDTO;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.FinancialTransactionRepositoryPort;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.api.FinancialTransactionService;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.exception.ErrorCode;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.exception.AppRuntimeException;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.mapper.FinancialTransactionModelMapper;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class FinancialTransactionServiceImpl implements FinancialTransactionService {

    private final FinancialTransactionRepositoryPort financialTransactionRepositoryPort;
    private final FinancialTransactionModelMapper financialTransactionModelMapper;
    private final WalletRepository walletRepository;

    @Override
    public FinancialTransactionDTO createFinancialTransaction(@Valid CreateFinancialTransactionDTO
                                                                          createFinancialTransactionDTO) {
        Long walletId = createFinancialTransactionDTO.walletId();
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> {
            throw new AppRuntimeException(
                    ErrorCode.W003,
                    String.format("Wallet with id: %d does not exist", walletId));
        });
        FinancialTransaction financialTransaction = FinancialTransaction.builder()
                .financialTransactionType(createFinancialTransactionDTO.financialTransactionType())
                .transactionDate(Instant.now())
                .description(createFinancialTransactionDTO.description())
                .wallet(wallet)
                .amount(createFinancialTransactionDTO.amount())
                .build();

        FinancialTransaction savedFinancialTransaction = financialTransactionRepositoryPort.save(financialTransaction);
        return financialTransactionModelMapper.mapFinancialTransactionEntityToFinancialTransactionDTO(savedFinancialTransaction);
    }

    @Override
    public List<FinancialTransactionDTO> getFinancialTransactionsByWalletId(@Min(1) @NotNull Long walletId) {
        return financialTransactionRepositoryPort.findAllByWalletIdOrderByTransactionDateDesc(walletId).stream()
                .map(financialTransactionModelMapper::mapFinancialTransactionEntityToFinancialTransactionDTO)
                .toList();
    }

    @Override
    public FinancialTransactionDTO findById(@Min(1) @NotNull Long id) {
        Optional<FinancialTransaction> financialTransaction = financialTransactionRepositoryPort.findById(id);
        return financialTransaction.map(financialTransactionModelMapper::mapFinancialTransactionEntityToFinancialTransactionDTO)
                .orElseThrow(() -> new AppRuntimeException(ErrorCode.FT001,
                        String.format("Financial transaction with id: %d not found", id))); //fixme
    }

    @Override
    public void deleteTransactionById(@Min(1) @NotNull Long id) {
        if (financialTransactionRepositoryPort.existsById(id)) {
            financialTransactionRepositoryPort.deleteById(id);
        } else {
            throw new AppRuntimeException(
                    ErrorCode.FT001,
                    String.format("FinancialTransaction with given id: %d does not exist", id));
        }
    }

    @Override
    @Transactional
    public FinancialTransactionDTO updateTransaction(
            @Min(1) @NotNull Long id,
            @Valid UpdateFinancialTransactionDTO updateTransactionDTO){

        FinancialTransaction financialTransaction = financialTransactionRepositoryPort.findById(id)
                .orElseThrow(()-> {
                    throw new AppRuntimeException(ErrorCode.FT001,
                            String.format("Financial transaction with id: %d not found", id));
                });

        financialTransaction.builder()
                .amount(updateTransactionDTO.amount())
                .description(updateTransactionDTO.description())
                .transactionDate(Instant.now()) // fixme  tutaj czy zapisujemy date udate czy moze uzytkowniksie pomylil przy wprowadzaniu daty
                .build();

        return financialTransactionModelMapper.mapFinancialTransactionEntityToFinancialTransactionDTO(financialTransaction);
    }
}
