package br.com.fsrocha.vrcontadigital.domain.service;

import br.com.fsrocha.vrcontadigital.domain.model.TransactionData;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionResult;

import java.math.BigDecimal;

public abstract class DebitTransactionHandler {
    protected DebitTransactionHandler next;

    public void setNextHandler(DebitTransactionHandler nextHandler) {
        this.next = nextHandler;
    }

    public abstract TransactionResult execute(TransactionData data);

    protected TransactionResult nextHandler(TransactionData data) {
        if (next == null) {
            return new TransactionResult(BigDecimal.ZERO);
        }
        return next.execute(data);
    }
}
