package br.com.fsrocha.vrcontadigital.domain.service;

import br.com.fsrocha.vrcontadigital.application.exception.NotFoundException;
import br.com.fsrocha.vrcontadigital.domain.enums.OperationType;
import br.com.fsrocha.vrcontadigital.domain.enums.TransactionStatus;
import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;
import br.com.fsrocha.vrcontadigital.domain.model.BalanceEntity;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionEntity;
import br.com.fsrocha.vrcontadigital.domain.repository.BalanceRepository;
import br.com.fsrocha.vrcontadigital.domain.repository.TransactionsRepository;
//import br.com.fsrocha.vrcontadigital.domain.service.impl.TransactionsServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class TransactionsServiceImplTest {

    @Mock
    AccountService accountService;

    @Mock
    BalanceRepository balanceRepository;

    @Mock
    TransactionsRepository transactionsRepository;


//    @InjectMocks
//    TransactionsServiceImpl transactionsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    @DisplayName("Deposit amount when valid transaction")
//    void testDepositAmountWhenValidTransaction() {
//        // Assemble
//        String keyPix = "0011";
//        String password = "0022";
//        BigDecimal amount = BigDecimal.valueOf(10.0);
//
//        var accountEntity = new AccountEntity();
//        var balanceEntity = new BalanceEntity();
//        balanceEntity.setBalance(amount);
//
//        Mockito.when(accountService.findAccountByKeyPixAndPassword(keyPix, password)).thenReturn(accountEntity);
//        Mockito.when(balanceRepository.save(Mockito.any(BalanceEntity.class))).thenReturn(balanceEntity);
//        Mockito.when(transactionsRepository.save(Mockito.any(TransactionEntity.class))).thenReturn(new TransactionEntity());
//
//        // Act
//        transactionsService.deposit(keyPix, password, amount);
//
//        // Verify
//        Mockito.verify(accountService).findAccountByKeyPixAndPassword(keyPix, password);
//        Mockito.verify(balanceRepository).save(Mockito.any(BalanceEntity.class));
//        Mockito.verify(transactionsRepository).save(Mockito.any(TransactionEntity.class));
//        Assertions.assertEquals(amount, balanceEntity.getBalance());
//    }
//
//    @Test
//    @DisplayName("Create a deposit and check transaction data")
//    void testCreateDepositAndCheckTransactionData() {
//        // Assemble
//        String keyPix = "0011";
//        String password = "0022";
//        BigDecimal amount = BigDecimal.valueOf(10.0);
//
//        Mockito.when(accountService.findAccountByKeyPixAndPassword(keyPix, password)).thenReturn(new AccountEntity());
//        Mockito.when(balanceRepository.save(Mockito.any(BalanceEntity.class))).thenReturn(new BalanceEntity());
//
//        // Act
//        transactionsService.deposit(keyPix, password, amount);
//
//        // Assert
//        ArgumentCaptor<TransactionEntity> transactionCaptor = ArgumentCaptor.forClass(TransactionEntity.class);
//        Mockito.verify(transactionsRepository).save(transactionCaptor.capture());
//
//        var savedTransaction = transactionCaptor.getValue();
//
//        Assertions.assertNotNull(savedTransaction);
//        Assertions.assertEquals(amount, savedTransaction.getAmount());
//        Assertions.assertEquals(OperationType.CREDIT, savedTransaction.getOperationType());
//        Assertions.assertEquals(TransactionStatus.SUCCESS, savedTransaction.getStatus());
//    }
//
//    @Test
//    @DisplayName("Create new deposit with balance none exists")
//    void testCreateNewDepositWithBalanceNoneExists() {
//        // Assemble
//        String keyPix = "0011";
//        String password = "0022";
//        BigDecimal amount = BigDecimal.valueOf(10.0);
//
//        Mockito.when(accountService.findAccountByKeyPixAndPassword(keyPix, password))
//                .thenThrow(new NotFoundException());
//
//        // Act
//        Assertions.assertThrows(NotFoundException.class, () -> transactionsService.deposit(keyPix, password, amount));
//
//        // Assert
//        Mockito.verify(balanceRepository, Mockito.never()).save(Mockito.any(BalanceEntity.class));
//        Mockito.verify(transactionsRepository, Mockito.never()).save(Mockito.any(TransactionEntity.class));
//    }
//
//    @Test
//    @DisplayName("Create new deposit with exists balance")
//    void testCreateNewDepositExistsBalance() {
//        // Assemble
//        String keyPix = "0011";
//        String password = "0022";
//        BigDecimal amount = BigDecimal.valueOf(10.0);
//
//        var accountEntity = new AccountEntity();
//        var balanceEntity = new BalanceEntity(accountEntity, amount, LocalDateTime.now());
//        accountEntity.setBalance(balanceEntity);
//
//        Mockito.when(accountService.findAccountByKeyPixAndPassword(keyPix, password)).thenReturn(accountEntity);
//        Mockito.when(balanceRepository.save(Mockito.any(BalanceEntity.class))).thenReturn(balanceEntity);
//
//        // Act
//        transactionsService.deposit(keyPix, password, amount);
//
//        // Assert
//        ArgumentCaptor<TransactionEntity> transactionCaptor = ArgumentCaptor.forClass(TransactionEntity.class);
//        ArgumentCaptor<BalanceEntity> balanceCaptor = ArgumentCaptor.forClass(BalanceEntity.class);
//
//        Mockito.verify(transactionsRepository).save(transactionCaptor.capture());
//        Mockito.verify(balanceRepository).save(balanceCaptor.capture());
//
//        var savedTransaction = transactionCaptor.getValue();
//        var savedBalance = balanceCaptor.getValue();
//
//        Assertions.assertNotNull(savedBalance);
//        Assertions.assertEquals(BigDecimal.valueOf(20.0), savedBalance.getBalance());
//
//        Assertions.assertNotNull(savedTransaction);
//        Assertions.assertEquals(amount, savedTransaction.getAmount());
//        Assertions.assertEquals(OperationType.CREDIT, savedTransaction.getOperationType());
//        Assertions.assertEquals(TransactionStatus.SUCCESS, savedTransaction.getStatus());
//    }
//
//    @Test
//    @DisplayName("Cash withdrawal successfully")
//    void testCashWithdrawalSuccessfully() {
//        // Assemble
//
//        // Act
//
//    }

}
