package br.com.fsrocha.vrcontadigital.domain.service;

import br.com.fsrocha.vrcontadigital.domain.enums.OperationType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionFactory {

    ApplicationContext applicationContext;

    public TransactionExecutorService getTransactionExecutor(OperationType operationType) {
        return applicationContext.getBeansOfType(TransactionExecutorService.class)
                .values()
                .stream()
                .filter(service -> service.operationType() == operationType)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
