package br.com.fsrocha.vrcontadigital.domain.service.impl.debit;

import br.com.fsrocha.vrcontadigital.application.exception.InsufficientFundsException;
import br.com.fsrocha.vrcontadigital.domain.model.BalanceEntity;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionData;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionResult;
import br.com.fsrocha.vrcontadigital.domain.repository.BalanceRepository;
import br.com.fsrocha.vrcontadigital.domain.service.DebitTransactionHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CheckBalanceHandler extends DebitTransactionHandler {

    BalanceRepository balanceRepository;

    @Override
    public TransactionResult execute(TransactionData data) {
        applyDebit(data.getAccount().getBalance(), data.getAmount());
        return nextHandler(data);
    }

    private void applyDebit(BalanceEntity balanceEntity, BigDecimal amount) {
        var currentBalance = validAndDebitValue(balanceEntity, amount);
        balanceEntity.setBalance(currentBalance);
        balanceRepository.save(balanceEntity);
    }

    private BigDecimal validAndDebitValue(BalanceEntity balanceEntity, BigDecimal amount) {
        var currentBalance = balanceEntity.getBalance().subtract(amount);
        return Optional.of(currentBalance)
                .filter(v -> v.compareTo(BigDecimal.ZERO) >= 0)
                .orElseThrow(InsufficientFundsException::new);
    }
}
