package br.com.fsrocha.vrcontadigital.domain.model;

import br.com.fsrocha.vrcontadigital.domain.enums.OperationType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionData {

    String keyPix;
    BigDecimal amount;
    String password;
    OperationType operationType;
    AccountEntity account;

    public TransactionData(String keyPix, BigDecimal amount, String password) {
        this.keyPix = keyPix;
        this.amount = amount;
        this.password = password;
    }
}
