package pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.mapper;


import org.mapstruct.Mapper;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.WalletDTO;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository.Wallet;

@Mapper(componentModel = "spring")
public interface WalletModelMapper {
    WalletDTO mapWalletEntityToWalletDTO(Wallet wallet);
}


