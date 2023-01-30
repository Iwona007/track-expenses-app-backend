package pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto;

import pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.model.FinancialTransactionType;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CreateFinancialTransactionDTO(@Min(1) @NotNull Long walletId,
                                            @Digits(integer = 13, fraction = 2) @PositiveOrZero BigDecimal amount,
                                            String description,
                                            @NotNull FinancialTransactionType financialTransactionType) {
}
