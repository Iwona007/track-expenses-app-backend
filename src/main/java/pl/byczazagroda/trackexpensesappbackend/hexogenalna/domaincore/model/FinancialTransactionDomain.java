package pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.model;

import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository.Wallet;

import java.math.BigDecimal;
import java.time.Instant;

public class FinancialTransactionDomain {

    private Long id;

    private Wallet wallet;

    private FinancialTransactionType financialTransactionType;

    private BigDecimal amount;

    private Instant transactionDate;

    private String description;

    public FinancialTransactionDomain(Long id, Wallet wallet, FinancialTransactionType financialTransactionType,
                                      BigDecimal amount, Instant transactionDate, String description) {
        this.id = id;
        this.wallet = wallet;
        this.financialTransactionType = financialTransactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.description = description;
    }
}
