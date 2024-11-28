package br.com.fsrocha.vrcontadigital.application.controller;

import br.com.fsrocha.vrcontadigital.ApiExec;
import br.com.fsrocha.vrcontadigital.UnitTest;
import br.com.fsrocha.vrcontadigital.application.dto.request.TransactionRequest;
import br.com.fsrocha.vrcontadigital.application.dto.response.DebitAccountResponse;
import br.com.fsrocha.vrcontadigital.application.dto.response.ErrorResponse;
import br.com.fsrocha.vrcontadigital.domain.enums.TransactionStatus;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;

@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
class TransactionControllerTest extends UnitTest {

    static final String API = "/contascorrente/";

    @Test
    @WithMockUser
    @DisplayName("[200] - Depósito realizado com sucesso")
    void testDepositWithSuccess() throws Exception {
        // Assemble
        final var URI_DEPOSIT = API + "11111111111" + "/deposito-pix";
        var request = new TransactionRequest(BigDecimal.valueOf(10.0), "7204");

        // Act
        var result = ApiExec.doPost(mockMvc, URI_DEPOSIT, mapToJson(request));

        // Assert
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(result.getResponse().getContentAsString()).isEmpty();
    }

    @Test
    @WithMockUser
    @DisplayName("[404] - Caso a chave pix não corresponda a uma conta existente para depósito")
    void testDepositWithNotFoundAccount() throws Exception {
        // Assemble
        final var URI_DEPOSIT = API + "1111111111" + "/deposito-pix";
        var request = new TransactionRequest(BigDecimal.valueOf(10.0), "7204");

        // Act
        var result = ApiExec.doPost(mockMvc, URI_DEPOSIT, mapToJson(request));

        // Assert
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        Assertions.assertThat(result.getResponse().getContentAsString()).isEmpty();
    }

    @Test
    @WithMockUser
    @DisplayName("[400] - Caso alguns dos dados para depósitos esteja faltando")
    void testDepositWithBadRequest() throws Exception {
        // Assemble
        final var URI_DEPOSIT = API + "11111111111" + "/deposito-pix";
        var request = new TransactionRequest(BigDecimal.valueOf(10.0), "");

        // Act
        var result = ApiExec.doPost(mockMvc, URI_DEPOSIT, mapToJson(request));

        // Assert
        var expected = mapToDto(result.getResponse().getContentAsString(), ErrorResponse.class);

        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(expected)
                .isEqualTo(new ErrorResponse("senha is required"));
    }

    @Test
    @DisplayName("[401] - Tentativa de depósito sem autorização")
    void testDepositWithUnauthorized() throws Exception {
        // AssembleS
        final var URI_DEPOSIT = API + "11111111111" + "/deposito-pix";
        var request = new TransactionRequest(BigDecimal.valueOf(10.0), "");

        // Act
        var result = ApiExec.doPost(mockMvc, URI_DEPOSIT, mapToJson(request));

        // Assert
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        Assertions.assertThat(result.getResponse().getContentAsString()).isEmpty();
    }

    @Test
    @WithMockUser
    @DisplayName("[200] - Saque realizado com sucesso")
    void testCashWithdrawalSuccess() throws Exception {
        // Assemble
        final var URI_DEPOSIT = API + "11111111111" + "/saque";
        var request = new TransactionRequest(BigDecimal.valueOf(10.0), "7204");

        // Act
        var result = ApiExec.doPost(mockMvc, URI_DEPOSIT, mapToJson(request));

        // Assert
        var expected = mapToDto(result.getResponse().getContentAsString(), DebitAccountResponse.class);

        // Assert
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(expected)
                .isEqualTo(new DebitAccountResponse(BigDecimal.valueOf(485.15)));
    }

    @Test
    @WithMockUser
    @DisplayName("[422] - Caso a chave pix não correponsa a uma conta existente para saque")
    void testCashWithdrawalWithInvalidAccount() throws Exception {
        // Assemble
        final var URI_DEPOSIT = API + "1111111111" + "/saque";
        var request = new TransactionRequest(BigDecimal.valueOf(10.0), "7204");

        // Act
        var result = ApiExec.doPost(mockMvc, URI_DEPOSIT, mapToJson(request));

        // Assert

        // Assert
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
        Assertions.assertThat(result.getResponse().getContentAsString())
                .isEqualTo(TransactionStatus.ACCOUNT_NOT_FOUND.getTranslate());
    }

    @Test
    @WithMockUser
    @DisplayName("[422] - Caso de saldo insuficiente para saque")
    void testCashWithdrawalWithInsufficientBalance() throws Exception {
        // Assemble
        final var URI_DEPOSIT = API + "11111111111" + "/saque";
        var request = new TransactionRequest(BigDecimal.valueOf(600), "7204");

        // Act
        var result = ApiExec.doPost(mockMvc, URI_DEPOSIT, mapToJson(request));

        // Assert

        // Assert
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
        Assertions.assertThat(result.getResponse().getContentAsString())
                .isEqualTo(TransactionStatus.INSUFFICIENT_FUNDS.getTranslate());
    }

    @Test
    @DisplayName("[401] - Tentativa de saque sem autorização")
    void testCashWithdrawalWithUnauthorized() throws Exception {
        // AssembleS
        final var URI_DEPOSIT = API + "11111111111" + "/saque";
        var request = new TransactionRequest(BigDecimal.valueOf(10.0), "");

        // Act
        var result = ApiExec.doPost(mockMvc, URI_DEPOSIT, mapToJson(request));

        // Assert
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        Assertions.assertThat(result.getResponse().getContentAsString()).isEmpty();
    }
}
