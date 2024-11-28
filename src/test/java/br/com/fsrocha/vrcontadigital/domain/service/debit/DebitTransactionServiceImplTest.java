package br.com.fsrocha.vrcontadigital.domain.service.debit;

import br.com.fsrocha.vrcontadigital.domain.model.TransactionData;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionResult;
import br.com.fsrocha.vrcontadigital.domain.service.impl.debit.CheckAccountHandler;
import br.com.fsrocha.vrcontadigital.domain.service.impl.debit.CheckBalanceHandler;
import br.com.fsrocha.vrcontadigital.domain.service.impl.debit.DebitRegisterHandler;
import br.com.fsrocha.vrcontadigital.domain.service.impl.debit.DebitTransactionServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
class DebitTransactionServiceImplTest {

    @Mock
    CheckAccountHandler checkAccount;

    @Mock
    CheckBalanceHandler checkBalance;

    @Mock
    DebitRegisterHandler debitRegister;

    @InjectMocks
    DebitTransactionServiceImpl debitTransactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExecuteTransactionSuccess() {
        // Assemble
        var transactionData = Mockito.mock(TransactionData.class);
        var expectedResult = Mockito.mock(TransactionResult.class);

        when(checkAccount.execute(Mockito.any(TransactionData.class))).thenReturn(expectedResult);

        // Act
        var result = debitTransactionService.execute(transactionData);

        // Assert
        assertEquals(expectedResult, result);
        verify(checkAccount).setNextHandler(checkBalance);
        verify(checkBalance).setNextHandler(debitRegister);
        verify(checkAccount).execute(transactionData);
        verifyNoMoreInteractions(checkAccount, checkBalance, debitRegister);
    }

}
