package br.com.fsrocha.vrcontadigital.domain.service;

import br.com.fsrocha.vrcontadigital.domain.model.AccountEntity;

import java.math.BigDecimal;

public interface AccountService {

    AccountEntity createAccount(AccountEntity entity);

    BigDecimal checkBalance(String accountNumber);

    AccountEntity findAccountByKeyPixAndPassword(String keyPix, String password);
}
