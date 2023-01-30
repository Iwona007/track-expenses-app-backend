package pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.model;

import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository.FinancialTransaction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class WalletDomain {

    private Long id;
    private String name;
    private Instant creationDate;

    private List<FinancialTransaction> financialTransactionList = new ArrayList<>();

    public WalletDomain(String name) {
        this.name = name;
        this.creationDate = Instant.now();
    }
}
