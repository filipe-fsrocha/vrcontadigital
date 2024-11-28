package br.com.fsrocha.vrcontadigital.domain.service.impl.debit;

import br.com.fsrocha.vrcontadigital.application.exception.AccountNotFoundException;
import br.com.fsrocha.vrcontadigital.application.exception.NotFoundException;
import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionData;
import br.com.fsrocha.vrcontadigital.domain.model.TransactionResult;
import br.com.fsrocha.vrcontadigital.domain.service.AccountService;
import br.com.fsrocha.vrcontadigital.domain.service.DebitTransactionHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CheckAccountHandler extends DebitTransactionHandler {

    AccountService accountService;

    @Override
    public TransactionResult execute(TransactionData data) {
        var account = checkAccountAndReturn(data.getKeyPix(), data.getPassword());
        data.setAccount(account);
        return nextHandler(data);
    }

    private AccountEntity checkAccountAndReturn(String keyPix, String password) {
        try {
            return accountService.findAccountByKeyPixAndPassword(keyPix, password);
        } catch (NotFoundException e) {
            LOGGER.warn("Account not found");
            throw new AccountNotFoundException();
        }
    }
}
