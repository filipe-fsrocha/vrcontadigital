package br.com.fsrocha.vrcontadigital.domain.service.impl.debit;

import br.com.fsrocha.vrcontadigital.domain.enums.OperationType;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionData;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionResult;
import br.com.fsrocha.vrcontadigital.domain.service.DebitTransactionHandler;
import br.com.fsrocha.vrcontadigital.domain.service.TransactionExecutorService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DebitTransactionServiceImpl implements TransactionExecutorService {

    CheckAccountHandler checkAccount;
    CheckBalanceHandler checkBalance;
    DebitRegisterHandler debitRegister;

    @Override
    @Transactional
    public TransactionResult execute(TransactionData transactionData) {
        return createChainDebitExecutor().execute(transactionData);
    }

    @Override
    public OperationType operationType() {
        return OperationType.DEBIT;
    }

    private DebitTransactionHandler createChainDebitExecutor() {
        checkAccount.setNextHandler(checkBalance);
        checkBalance.setNextHandler(debitRegister);
        return checkAccount;
    }
}
