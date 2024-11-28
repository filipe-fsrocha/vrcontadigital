package br.com.fsrocha.vrcontadigital.domain.service.impl.debit;

import br.com.fsrocha.vrcontadigital.domain.enums.OperationType;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionData;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionResult;
import br.com.fsrocha.vrcontadigital.domain.service.DebitTransactionHandler;
import br.com.fsrocha.vrcontadigital.domain.service.RegisterTransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DebitRegisterHandler extends DebitTransactionHandler {

    RegisterTransactionService registerTransactionService;

    @Override
    public TransactionResult execute(TransactionData data) {
        var account = data.getAccount();
        registerTransactionService.registerTransaction(account, data.getAmount(), OperationType.DEBIT);
        return new TransactionResult(account.getBalance().getBalance());
    }

}
