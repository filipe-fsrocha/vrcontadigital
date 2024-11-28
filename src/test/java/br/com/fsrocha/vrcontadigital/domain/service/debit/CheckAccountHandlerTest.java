package br.com.fsrocha.vrcontadigital.domain.service.debit;

import br.com.fsrocha.vrcontadigital.application.exception.AccountNotFoundException;
import br.com.fsrocha.vrcontadigital.application.exception.NotFoundException;
import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;
import br.com.fsrocha.vrcontadigital.domain.model.BalanceEntity;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionData;
import br.com.fsrocha.vrcontadigital.domain.service.AccountService;
import br.com.fsrocha.vrcontadigital.domain.service.impl.debit.CheckAccountHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CheckAccountHandlerTest {

    @Mock
    AccountService accountService;

    @InjectMocks
    CheckAccountHandler checkAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckAccountExistsSuccess() {
        // Assemble
        var data = new TransactionData("0011", BigDecimal.valueOf(10), "1234");
        var accountEntity = new AccountEntity();
        var balanceEntity = new BalanceEntity(accountEntity, BigDecimal.valueOf(10), LocalDateTime.now());
        accountEntity.setBalance(balanceEntity);

        when(accountService.findAccountByKeyPixAndPassword("0011", "1234")).thenReturn(accountEntity);

        // Act
        var result = checkAccount.execute(data);

        // Assert
        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(0), result.getCurrentAmount());

        verify(accountService).findAccountByKeyPixAndPassword("0011", "1234");
    }

    @Test
    void testCheckAccountNotFound() {
        // Assemble
        var data = new TransactionData("0011", BigDecimal.valueOf(10), "1234");

        when(accountService.findAccountByKeyPixAndPassword("0011", "1234"))
                .thenThrow(new NotFoundException());

        // Act
        assertThrows(AccountNotFoundException.class, () -> checkAccount.execute(data));

        // Assert
        verify(accountService).findAccountByKeyPixAndPassword("0011", "1234");
    }
}
