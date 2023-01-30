package pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository;

import org.springframework.data.jpa.repository.JpaRepository;


public interface WalletRepository extends JpaRepository<Wallet, Long> {

//    List<Wallet> findAllByNameLikeIgnoreCase(String name);
//    List<Wallet> findAllByOrderByNameAsc();
}
