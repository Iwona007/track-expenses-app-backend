package pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore;

import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository.WalletRepository;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository.Wallet;

import java.util.List;

public interface WalletBusinessRepository extends WalletRepository {

    List<Wallet> findAllByNameLikeIgnoreCase(String name);
    List<Wallet> findAllByOrderByNameAsc();
}
