package br.com.fsrocha.vrcontadigital.domain.enums;

import lombok.Getter;

@SuppressWarnings("SpellCheckingInspection")
@Getter
public enum TransactionStatus {
    SUCCESS("SUCESSO"),
    FAILED("FALHA"),
    INSUFFICIENT_FUNDS("SALDO_INSUFICIENTE"),
    ACCOUNT_NOT_FOUND("CONTA_NAO_ENCONTRADA");

    private final String translate;

    TransactionStatus(String translate) {
        this.translate = translate;
    }
}
