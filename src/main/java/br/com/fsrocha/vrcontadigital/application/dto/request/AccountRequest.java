package br.com.fsrocha.vrcontadigital.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRequest {

    @NotBlank(message = "cpf is required")
    String cpf;

    @NotBlank(message = "nome is required")
    String nome;
}
