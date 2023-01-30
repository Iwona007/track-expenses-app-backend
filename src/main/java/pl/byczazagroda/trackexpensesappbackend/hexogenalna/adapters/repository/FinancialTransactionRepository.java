package pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancialTransactionRepository extends JpaRepository<FinancialTransaction, Long> {
//    List<FinancialTransaction> findAllByWalletIdOrderByTransactionDateDesc(Long id);
}
