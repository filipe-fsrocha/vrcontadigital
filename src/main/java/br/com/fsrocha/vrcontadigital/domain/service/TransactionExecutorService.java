package br.com.fsrocha.vrcontadigital.domain.service;

import br.com.fsrocha.vrcontadigital.domain.enums.OperationType;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionData;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionResult;

public interface TransactionExecutorService {

    TransactionResult execute(TransactionData transactionData);

    OperationType operationType();

}
