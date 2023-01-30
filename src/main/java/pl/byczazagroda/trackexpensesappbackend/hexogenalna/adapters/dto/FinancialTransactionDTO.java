package pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto;

import pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.model.FinancialTransactionType;

import java.math.BigDecimal;
import java.time.Instant;

public record FinancialTransactionDTO(Long id, BigDecimal amount, String description,
                                      FinancialTransactionType financialTransactionType, Instant transactionDate) {
}
