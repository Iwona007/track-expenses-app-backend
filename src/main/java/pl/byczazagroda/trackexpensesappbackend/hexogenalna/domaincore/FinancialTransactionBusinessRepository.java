package pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository.FinancialTransactionRepository;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository.FinancialTransaction;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.model.FinancialTransactionType;

import java.util.List;

@Repository
public interface FinancialTransactionBusinessRepository extends FinancialTransactionRepository {

    List<FinancialTransaction> findAllByWalletIdOrderByTransactionDateDesc(Long id);

    @Query(
            """
                    SELECT ft FROM FinancialTransaction ft 
                    WHERE ft.financialTransactionType = :finTransType
                    """
    )
    List<FinancialTransaction> findAllFinTransByTransactionType(@Param("finTransType")
                                                                FinancialTransactionType finTransType);
}
