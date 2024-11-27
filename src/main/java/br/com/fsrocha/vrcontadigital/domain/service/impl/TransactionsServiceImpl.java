package br.com.fsrocha.vrcontadigital.domain.service.impl;

import br.com.fsrocha.vrcontadigital.domain.enums.OperationType;
import br.com.fsrocha.vrcontadigital.domain.enums.TransactionStatus;
import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;
import br.com.fsrocha.vrcontadigital.domain.model.BalanceEntity;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionEntity;
import br.com.fsrocha.vrcontadigital.domain.repository.BalanceRepository;
import br.com.fsrocha.vrcontadigital.domain.repository.TransactionsRepository;
import br.com.fsrocha.vrcontadigital.domain.service.AccountService;
import br.com.fsrocha.vrcontadigital.domain.service.TransactionsService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionsServiceImpl implements TransactionsService {

    AccountService accountService;
    BalanceRepository balanceRepository;
    TransactionsRepository transactionsRepository;

    @Override
    @Transactional
    public void deposit(String keyPix, String password, BigDecimal amount) {
        var account = accountService.findAccountByKeyPixAndPassword(keyPix, password);
        var balanceEntity = createOrAddBalance(account, amount);

        createTransaction(account, amount);
        balanceRepository.save(balanceEntity);
    }

    private BalanceEntity createOrAddBalance(AccountEntity account, BigDecimal amount) {
        return Optional.ofNullable(account.getBalance())
                .map(balanceEntity -> {
                    var newBalance = balanceEntity.getBalance().add(amount);
                    balanceEntity.setBalance(newBalance);
                    return balanceEntity;
                })
                .orElse(new BalanceEntity(account, amount, LocalDateTime.now()));
    }

    private void createTransaction(AccountEntity account, BigDecimal amount) {
        var transactionEntity = new TransactionEntity(account, amount);
        transactionEntity.setOperationType(OperationType.CREDIT);
        transactionEntity.setStatus(TransactionStatus.SUCCESS);
        transactionEntity.setLocalDateTime(LocalDateTime.now());
        transactionsRepository.save(transactionEntity);
    }
}
