package br.com.fsrocha.vrcontadigital.domain.service.debit;

import br.com.fsrocha.vrcontadigital.application.exception.AccountNotFoundException;
import br.com.fsrocha.vrcontadigital.application.exception.InsufficientFundsException;
import br.com.fsrocha.vrcontadigital.application.exception.NotFoundException;
import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;
import br.com.fsrocha.vrcontadigital.domain.model.BalanceEntity;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionData;
import br.com.fsrocha.vrcontadigital.domain.repository.BalanceRepository;
import br.com.fsrocha.vrcontadigital.domain.service.impl.debit.CheckBalanceHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CheckBalanceHandlerTest {

    @Mock
    BalanceRepository balanceRepository;

    @InjectMocks
    CheckBalanceHandler checkBalance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckBalanceIsSufficient() {
        // Assemble
        var account = new AccountEntity();
        var balance = new BalanceEntity();
        balance.setBalance(BigDecimal.valueOf(10));
        account.setBalance(balance);

        var data = new TransactionData("000", BigDecimal.valueOf(5), "000");
        data.setAccount(account);

        // Act
        var result = checkBalance.execute(data);

        // Assert
        ArgumentCaptor<BalanceEntity> balanceCaptor = ArgumentCaptor.forClass(BalanceEntity.class);

        Mockito.verify(balanceRepository).save(balanceCaptor.capture());

        var savedBalance = balanceCaptor.getValue();
        assertEquals(BigDecimal.valueOf(5), savedBalance.getBalance());

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(0), result.getCurrentAmount());
    }

    @Test
    void testCheckBalanceInsufficient() {
        // Assemble
        var account = new AccountEntity();
        var balance = new BalanceEntity();
        balance.setBalance(BigDecimal.valueOf(2));
        account.setBalance(balance);

        var data = new TransactionData("0011", BigDecimal.valueOf(10), "1234");
        data.setAccount(account);

        // Act
        assertThrows(InsufficientFundsException.class, () -> checkBalance.execute(data));

        // Assert
        verify(balanceRepository, Mockito.never()).save(Mockito.any(BalanceEntity.class));
    }
}
