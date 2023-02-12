package pl.byczazagroda.trackexpensesappbackend.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.validation.annotation.Validated;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.api.WalletController;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.UpdateWalletDTO;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.WalletDTO;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.error.ErrorResponse;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.mapper.WalletModelMapper;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.WalletBusinessRepository;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository.Wallet;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository.WalletRepository;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository.WalletServiceImpl;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@Validated
@WebMvcTest(
        controllers = WalletController.class,
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {WalletRepository.class, WalletServiceImpl.class}))
class WalletUpdateServiceImplTest {

    public static final long ID_1L = 1L;

    private static final String NAME_1 = "wallet name one";

    private static final String NAME_2 = "wallet name two";

    private static final Instant DATE_NOW = Instant.now();

    @MockBean
    private ErrorResponse errorResponse;

    @MockBean
    private WalletBusinessRepository walletBusinessRepository;

    @Autowired
    private WalletServiceImpl walletService;

    @MockBean
    private WalletModelMapper walletModelMapper;

    @Test
    @DisplayName("when finding wallet by id should update wallet")
    void shouldUpdateWallet_whenFindWalletById() {
        // given
        UpdateWalletDTO updateWalletDto = new UpdateWalletDTO(NAME_1);
        Wallet wallet = new Wallet(NAME_2);
        wallet.setId(ID_1L);
        wallet.setCreationDate(DATE_NOW);
        WalletDTO newWalletDTO = new WalletDTO(ID_1L, NAME_1, DATE_NOW);
        given(walletBusinessRepository.findById(ID_1L)).willReturn(Optional.of(wallet));
        given(walletModelMapper.mapWalletEntityToWalletDTO(wallet)).willReturn(newWalletDTO);

        // when
        WalletDTO walletDTO = walletService.updateWallet(ID_1L, updateWalletDto);

        // then
        assertThat(walletDTO.name()).isEqualTo(updateWalletDto.name());
    }

}
