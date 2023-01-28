package pl.byczazagroda.trackexpensesappbackend.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import pl.byczazagroda.trackexpensesappbackend.BaseControllerTestIT;
import pl.byczazagroda.trackexpensesappbackend.model.Wallet;

import javax.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

//@TestExecutionListeners(value = {SqlScriptsTestExecutionListener.class},
//        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@Sql(scripts = {"classpath:db/insert_into_wallets.sql"})
@Transactional
class WalletRepositoryTest extends BaseControllerTestIT {

    @Autowired
    private WalletRepository walletRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAllByNameLikeIgnoreCase() {
        List<Wallet> allWallets = this.walletRepository.findAll();
        List<Wallet> name_wallet = this.walletRepository.findAllByNameLikeIgnoreCase("Name_Wallet");
        assertThat(allWallets.size() , is(7));

        List<Wallet> name_wallet1 = name_wallet.stream().filter(e -> e.getName().equals("Name_Wallet")).collect(Collectors.toList());
        assertEquals(name_wallet1.size() , 2);
    }

    @Test
    void findAllByOrderByNameAsc() {
    }
}