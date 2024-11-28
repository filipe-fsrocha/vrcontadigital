package br.com.fsrocha.vrcontadigital.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionResult {
    BigDecimal currentAmount;

    public TransactionResult(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }
}
