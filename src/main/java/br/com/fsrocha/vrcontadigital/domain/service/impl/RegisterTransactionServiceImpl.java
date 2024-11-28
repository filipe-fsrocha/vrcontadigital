package br.com.fsrocha.vrcontadigital.domain.service.impl;

import br.com.fsrocha.vrcontadigital.domain.enums.OperationType;
import br.com.fsrocha.vrcontadigital.domain.enums.TransactionStatus;
import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionEntity;
import br.com.fsrocha.vrcontadigital.domain.repository.TransactionsRepository;
import br.com.fsrocha.vrcontadigital.domain.service.RegisterTransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterTransactionServiceImpl implements RegisterTransactionService {

    TransactionsRepository transactionsRepository;

    @Override
    public void registerTransaction(AccountEntity account, BigDecimal amount, OperationType operationType) {
        var transactionEntity = new TransactionEntity(account, amount);
        transactionEntity.setStatus(TransactionStatus.SUCCESS);
        transactionEntity.setOperationType(operationType);
        transactionEntity.setLocalDateTime(LocalDateTime.now());
        transactionsRepository.save(transactionEntity);
    }

}
