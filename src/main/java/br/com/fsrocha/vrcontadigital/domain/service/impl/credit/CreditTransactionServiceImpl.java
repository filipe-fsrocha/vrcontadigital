package br.com.fsrocha.vrcontadigital.domain.service.impl.credit;

import br.com.fsrocha.vrcontadigital.domain.enums.OperationType;
import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;
import br.com.fsrocha.vrcontadigital.domain.model.BalanceEntity;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionData;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionResult;
import br.com.fsrocha.vrcontadigital.domain.repository.BalanceRepository;
import br.com.fsrocha.vrcontadigital.domain.service.AccountService;
import br.com.fsrocha.vrcontadigital.domain.service.RegisterTransactionService;
import br.com.fsrocha.vrcontadigital.domain.service.TransactionExecutorService;
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
public class CreditTransactionServiceImpl implements TransactionExecutorService {

    AccountService accountService;
    BalanceRepository balanceRepository;
    RegisterTransactionService registerTransactionService;

    @Override
    @Transactional
    public TransactionResult execute(TransactionData transactionData) {
        return applyCredit(transactionData);
    }

    @Override
    public OperationType operationType() {
        return OperationType.CREDIT;
    }

    private TransactionResult applyCredit(TransactionData data) {
        var account = accountService.findAccountByKeyPixAndPassword(data.getKeyPix(), data.getPassword());
        var balanceEntity = createOrAddBalance(account, data.getAmount());
        registerTransactionService.registerTransaction(account, data.getAmount(), OperationType.CREDIT);
        return new TransactionResult(balanceRepository.save(balanceEntity).getBalance());
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
}
