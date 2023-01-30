package pl.byczazagroda.trackexpensesappbackend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.FinancialTransactionDTO;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.FinancialTransactionBusinessRepository;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.exception.AppRuntimeException;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.exception.ErrorCode;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.dto.mapper.FinancialTransactionModelMapper;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository.FinancialTransaction;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.adapters.repository.Wallet;
import pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.usecase.FinancialTransactionServiceImpl;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static pl.byczazagroda.trackexpensesappbackend.hexogenalna.domaincore.model.FinancialTransactionType.EXPENSE;

@ExtendWith(MockitoExtension.class)
class FinancialTransactionServiceImplTest {
    public static final long ID_1L = 1L;
    public static final long ID_2L = 2L;

    public static final long ID_10L = 10L;
    public static final Instant DATE_NOW = Instant.now();


    @Mock
    private FinancialTransactionBusinessRepository financialTransactionBusinessRepository;

    @InjectMocks
    private FinancialTransactionServiceImpl financialTransactionService;

    @Mock
    private FinancialTransactionModelMapper financialTransactionModelMapper;


    @Test
    @DisplayName("when finding with proper wallet transaction id should successfully find transactions")
    void shouldSuccessfullyFindFinancialTransactions_WhenWalletIdIsGiven() {
        //given
        FinancialTransaction financialTransaction1 = createFinancialTransaction();
        financialTransaction1.setId(ID_1L);
        FinancialTransactionDTO financialTransactionDTO1 = new FinancialTransactionDTO(ID_1L, BigDecimal.ONE, "desc", EXPENSE, DATE_NOW);

        FinancialTransaction financialTransaction2 = new FinancialTransaction();
        financialTransaction2.setId(ID_2L);
        financialTransaction2.setFinancialTransactionType(EXPENSE);
        financialTransaction2.setTransactionDate(DATE_NOW);

        FinancialTransactionDTO financialTransactionDTO2 = new FinancialTransactionDTO(ID_2L, BigDecimal.ONE, "desc", EXPENSE, DATE_NOW);

        List<FinancialTransaction> financialTransactionsList = new ArrayList<>();
        financialTransactionsList.add(financialTransaction1);
        financialTransactionsList.add(financialTransaction2);

        Wallet wallet = new Wallet("Random wallet");
        wallet.setId(ID_1L);
        wallet.setCreationDate(DATE_NOW);

        financialTransaction1.setWallet(wallet);
        financialTransaction2.setWallet(wallet);

        wallet.setFinancialTransactionList(financialTransactionsList);


        //when
        when(financialTransactionBusinessRepository.findAllByWalletIdOrderByTransactionDateDesc(ID_1L)).thenReturn(financialTransactionsList);
        when(financialTransactionModelMapper.mapFinancialTransactionEntityToFinancialTransactionDTO(financialTransaction1)).thenReturn(financialTransactionDTO1);
        when(financialTransactionModelMapper.mapFinancialTransactionEntityToFinancialTransactionDTO(financialTransaction2)).thenReturn(financialTransactionDTO2);

        List<FinancialTransactionDTO> returnedFinancialTransactionDTOsList = financialTransactionService.getFinancialTransactionsByWalletId(ID_1L);

        //then
        Assertions.assertEquals(returnedFinancialTransactionDTOsList.get(0), financialTransactionDTO1);
        Assertions.assertEquals(returnedFinancialTransactionDTOsList.get(1), financialTransactionDTO2);
    }


    @Test
    @DisplayName("when financial transaction id doesn't exist should not return transaction")
    void shouldNotReturnFinancialTransactionById_WhenIdNotExist() {
        //given
        FinancialTransaction financialTransaction = new FinancialTransaction();
        financialTransaction.setId(ID_1L);
        financialTransaction.setFinancialTransactionType(EXPENSE);
        financialTransaction.setTransactionDate(DATE_NOW);

        //when
        given(financialTransactionBusinessRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> financialTransactionService.findById(ID_10L)).isInstanceOf(AppRuntimeException.class);
        assertThatExceptionOfType(AppRuntimeException.class).isThrownBy(() -> financialTransactionService.findById(ID_10L)).withMessage(ErrorCode.FT001.getBusinessMessage());

    }

    @Test
    @DisplayName("when finding with proper financial transaction id should successfully find transaction")
    void shouldSuccessfullyFindFinancialTransaction_WhenFindingWithProperTransactionId() {
        //given
        FinancialTransaction financialTransaction = new FinancialTransaction();
        financialTransaction.setId(ID_1L);

        FinancialTransactionDTO financialTransactionDTO = new FinancialTransactionDTO(ID_1L, BigDecimal.valueOf(20), "description", EXPENSE, DATE_NOW);

        //when
        when(financialTransactionBusinessRepository.findById(ID_1L)).thenReturn(Optional.of(financialTransaction));
        when(financialTransactionModelMapper.mapFinancialTransactionEntityToFinancialTransactionDTO(financialTransaction)).thenReturn(financialTransactionDTO);
        FinancialTransactionDTO foundTransaction = financialTransactionService.findById(ID_1L);

        //then
        Assertions.assertEquals(financialTransactionDTO, foundTransaction);
    }

    @Test
    @DisplayName("when deleting financial transaction that does not exist should throw an exception")
    void ShouldThrowAnException_WhenGivenTransactionDoesNotExist() {
        //given
        //when
        when(financialTransactionBusinessRepository.existsById(ID_1L)).thenReturn(false);

        //then
        Assertions.assertThrows(AppRuntimeException.class, () -> financialTransactionService.deleteTransactionById(ID_1L));
    }

    private FinancialTransaction createFinancialTransaction() {
        FinancialTransaction financialTransaction1 = new FinancialTransaction();
        financialTransaction1.setId(ID_1L);
        financialTransaction1.setFinancialTransactionType(EXPENSE);
        financialTransaction1.setTransactionDate(DATE_NOW);
        return financialTransaction1;
    }

}