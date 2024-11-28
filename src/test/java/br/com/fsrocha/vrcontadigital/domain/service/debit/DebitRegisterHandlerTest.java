package br.com.fsrocha.vrcontadigital.domain.service.debit;

import br.com.fsrocha.vrcontadigital.domain.enums.OperationType;
import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;
import br.com.fsrocha.vrcontadigital.domain.model.BalanceEntity;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionData;
import br.com.fsrocha.vrcontadigital.domain.service.RegisterTransactionService;
import br.com.fsrocha.vrcontadigital.domain.service.impl.debit.DebitRegisterHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

class DebitRegisterHandlerTest {

    @Mock
    RegisterTransactionService registerTransactionService;

    @InjectMocks
    DebitRegisterHandler debitRegister;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterDebitSuccess() {
        // Assemble
        var account = new AccountEntity();
        var balance = new BalanceEntity();
        balance.setBalance(BigDecimal.valueOf(10));
        account.setBalance(balance);

        var data = new TransactionData("000", BigDecimal.valueOf(10), "0000");
        data.setAccount(account);

        // Act
        var result = debitRegister.execute(data);

        // Assert
        assertEquals(balance.getBalance(), result.getCurrentAmount());
        verify(registerTransactionService)
                .registerTransaction(Mockito.any(AccountEntity.class), Mockito.any(), Mockito.eq(OperationType.DEBIT));
    }
}
