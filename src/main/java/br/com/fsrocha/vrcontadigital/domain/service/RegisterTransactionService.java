package br.com.fsrocha.vrcontadigital.domain.service;

import br.com.fsrocha.vrcontadigital.domain.enums.OperationType;
import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;

import java.math.BigDecimal;

public interface RegisterTransactionService {

    void registerTransaction(AccountEntity account, BigDecimal amount, OperationType operationType);

}
