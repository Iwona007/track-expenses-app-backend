package pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.FinancialTransactionRepositoryPort;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.model.FinancialTransactionType;

import java.util.List;


public class FinancialTransactionRepository implements FinancialTransactionRepositoryPort{
    List<FinancialTransaction> findAllByWalletIdOrderByTransactionDateDesc(Long id);

    @Query(""" SELECT ft FROM FinancialTransaction ft 
                    WHERE ft.financialTransactionType = :finTransType
                    """)
    List<FinancialTransaction> findAllFinTransByTransactionType(@Param("finTransType")
                                                                FinancialTransactionType finTransType);
}
