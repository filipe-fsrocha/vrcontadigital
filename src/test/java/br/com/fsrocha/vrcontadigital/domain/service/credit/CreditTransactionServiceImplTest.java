package br.com.fsrocha.vrcontadigital.domain.service.credit;

import br.com.fsrocha.vrcontadigital.application.exception.NotFoundException;
import br.com.fsrocha.vrcontadigital.domain.enums.OperationType;
import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;
import br.com.fsrocha.vrcontadigital.domain.model.BalanceEntity;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionData;
import br.com.fsrocha.vrcontadigital.domain.repository.BalanceRepository;
import br.com.fsrocha.vrcontadigital.domain.service.AccountService;
import br.com.fsrocha.vrcontadigital.domain.service.RegisterTransactionService;
import br.com.fsrocha.vrcontadigital.domain.service.impl.credit.CreditTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreditTransactionServiceImplTest {

    @Mock
    AccountService accountService;

    @Mock
    BalanceRepository balanceRepository;

    @Mock
    RegisterTransactionService registerTransactionService;

    @InjectMocks
    CreditTransactionServiceImpl creditTransactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDepositWithSuccess() {
        // Assemble
        String keyPix = "0011";
        String password = "0022";
        BigDecimal amount = BigDecimal.valueOf(10.0);

        var transaction = new TransactionData(keyPix, amount, password);

        var accountEntity = new AccountEntity();
        var balanceEntity = new BalanceEntity();
        balanceEntity.setBalance(amount);

        when(accountService.findAccountByKeyPixAndPassword(keyPix, password)).thenReturn(accountEntity);
        when(balanceRepository.save(Mockito.any(BalanceEntity.class))).thenReturn(balanceEntity);

        // Atc
        var result = creditTransactionService.execute(transaction);

        // Assert
        ArgumentCaptor<BalanceEntity> balanceCaptor = ArgumentCaptor.forClass(BalanceEntity.class);

        Mockito.verify(balanceRepository).save(balanceCaptor.capture());

        var savedBalance = balanceCaptor.getValue();

        assertEquals(amount, result.getCurrentAmount());
        assertEquals(amount, savedBalance.getBalance());

        verify(accountService).findAccountByKeyPixAndPassword(keyPix, password);
        verify(registerTransactionService).registerTransaction(accountEntity, amount, OperationType.CREDIT);
    }

    @Test
    void testDepositWithoutBalancePreRegistered() {
        // Assemble
        String keyPix = "0011";
        String password = "0022";
        BigDecimal amount = BigDecimal.valueOf(10.0);

        var transaction = new TransactionData(keyPix, amount, password);

        var accountEntity = new AccountEntity();
        var balanceEntity = new BalanceEntity();

        when(accountService.findAccountByKeyPixAndPassword(keyPix, password)).thenReturn(accountEntity);
        when(balanceRepository.save(Mockito.any(BalanceEntity.class))).thenReturn(balanceEntity);

        // Atc
        var result = creditTransactionService.execute(transaction);

        // Assert
        ArgumentCaptor<BalanceEntity> balanceCaptor = ArgumentCaptor.forClass(BalanceEntity.class);

        Mockito.verify(balanceRepository).save(balanceCaptor.capture());

        var savedBalance = balanceCaptor.getValue();

        assertNotNull(result);
        assertEquals(amount, savedBalance.getBalance());

        verify(accountService).findAccountByKeyPixAndPassword(keyPix, password);
        verify(registerTransactionService).registerTransaction(accountEntity, amount, OperationType.CREDIT);
    }

    @Test
    void testDepositWithAccountNotExists() {
        // Assemble
        String keyPix = "0011";
        String password = "0022";
        BigDecimal amount = BigDecimal.valueOf(10.0);

        var transaction = new TransactionData(keyPix, amount, password);

        Mockito.when(accountService.findAccountByKeyPixAndPassword(keyPix, password))
                .thenThrow(new NotFoundException());

        // Act
        assertThrows(NotFoundException.class, () -> creditTransactionService.execute(transaction));

        // Assert
        verify(balanceRepository, Mockito.never()).save(Mockito.any(BalanceEntity.class));
        verify(registerTransactionService, Mockito.never())
                .registerTransaction(Mockito.any(AccountEntity.class), Mockito.eq(amount), Mockito.eq(OperationType.CREDIT));
    }
}
