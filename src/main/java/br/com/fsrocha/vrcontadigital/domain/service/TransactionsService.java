package br.com.fsrocha.vrcontadigital.domain.service;

import java.math.BigDecimal;

public interface TransactionsService {

    void deposit(String keyPix, String password, BigDecimal amount);

}
