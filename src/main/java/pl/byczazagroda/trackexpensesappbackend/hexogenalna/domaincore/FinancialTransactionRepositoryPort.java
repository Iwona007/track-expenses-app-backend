package pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore;

import pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.model.FinancialTransactionDomain;

import java.util.List;


public interface FinancialTransactionRepositoryPort {

    List<FinancialTransactionDomain> findAllByWalletIdOrderByTransactionDateDesc(Long id);

}
