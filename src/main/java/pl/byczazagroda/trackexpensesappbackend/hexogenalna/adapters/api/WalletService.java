package pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.api;


import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.CreateWalletDTO;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.UpdateWalletDTO;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.WalletDTO;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Validated
public interface WalletService {

    WalletDTO createWallet(@Valid CreateWalletDTO createWalletDTO);

    WalletDTO updateWallet(@Min(1) @NotNull Long id, @Valid UpdateWalletDTO walletToUpdate);

    List<WalletDTO> getWallets();

    void deleteWalletById(@Min(1) @NotNull Long id);

    WalletDTO findById(@Min(1) @NotNull Long id);

    List<WalletDTO> findAllByNameLikeIgnoreCase(@NotBlank @Length(max = 20) @Pattern(regexp = "[\\w ]+") String name);
}
