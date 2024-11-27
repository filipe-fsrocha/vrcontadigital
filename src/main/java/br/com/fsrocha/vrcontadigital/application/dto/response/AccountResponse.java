package br.com.fsrocha.vrcontadigital.application.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@SuppressWarnings("SpellCheckingInspection")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {
    String banco;
    String conta;
    String cartao;
    String senha;
}


