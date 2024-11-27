package br.com.fsrocha.vrcontadigital.application.controller;

import br.com.fsrocha.vrcontadigital.ApiExec;
import br.com.fsrocha.vrcontadigital.UnitTest;
import br.com.fsrocha.vrcontadigital.application.dto.request.TransactionRequest;
import br.com.fsrocha.vrcontadigital.application.dto.response.ErrorResponse;
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
    @DisplayName("[200] - Deposit with successfully")
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
    @DisplayName("[404] - Deposit with not found account")
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
    @DisplayName("[400] - Deposit invalid payload")
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
    @DisplayName("[401] - Authentication failed")
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
}
