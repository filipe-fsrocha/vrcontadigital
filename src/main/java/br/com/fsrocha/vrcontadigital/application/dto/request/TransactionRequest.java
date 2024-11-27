package br.com.fsrocha.vrcontadigital.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@SuppressWarnings("SpellCheckingInspection")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionRequest {

    @NotNull(message = "valor is required")
    BigDecimal valor;

    @NotBlank(message = "senha is required")
    String senha;

    public TransactionRequest(BigDecimal valor, String senha) {
        this.valor = valor;
        this.senha = senha;
    }
}
