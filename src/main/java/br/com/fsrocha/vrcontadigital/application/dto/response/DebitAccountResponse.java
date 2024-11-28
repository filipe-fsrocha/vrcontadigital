package br.com.fsrocha.vrcontadigital.application.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@SuppressWarnings("SpellCheckingInspection")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DebitAccountResponse {
    BigDecimal saldo;

    public DebitAccountResponse() {
    }

    public DebitAccountResponse(BigDecimal amount) {
        this.saldo = amount;
    }
}
