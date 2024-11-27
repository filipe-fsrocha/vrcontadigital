package br.com.fsrocha.vrcontadigital.application.controller;

import br.com.fsrocha.vrcontadigital.ApiExec;
import br.com.fsrocha.vrcontadigital.UnitTest;
import br.com.fsrocha.vrcontadigital.application.dto.request.AccountRequest;
import br.com.fsrocha.vrcontadigital.application.dto.response.AccountResponse;
import br.com.fsrocha.vrcontadigital.application.dto.response.ErrorResponse;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;

@AutoConfigureMockMvc
@FieldDefaults(level = AccessLevel.PRIVATE)
class AccountControllerTest extends UnitTest {

    static final String API = "/contascorrente";
    static final String BANK = "610";

    @Autowired
    AccountController accountController;

    @Test
    @DisplayName("[401] - Authentication failed")
    void testCreateAccountWithUnauthorizedAccess() throws Exception {
        // Assemble
        var request = new AccountRequest();
        request.setCpf("11111111111");
        request.setNome("VR Benefícios");

        // Act
        var result = ApiExec.doPost(mockMvc, API, mapToJson(request));

        // Assert
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    @WithMockUser
    @DisplayName("[201] - Account created successfully")
    void testCreateAccountSuccess() throws Exception {
        // Assemble
        var request = new AccountRequest();
        request.setCpf("11111111112");
        request.setNome("VR Benefícios");

        // Act
        var result = ApiExec.doPost(mockMvc, API, mapToJson(request));

        // Assert
        var expected = mapToDto(result.getResponse().getContentAsString(), AccountResponse.class);

        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());
        Assertions.assertThat(expected)
                .usingRecursiveComparison()
                .ignoringFields("cartao", "senha")
                .isEqualTo(createAccountResponse());
    }

    @Test
    @WithMockUser
    @DisplayName("[400] - Create account with invalid payload")
    void testCreateAccountWithInvalidPayload() throws Exception {
        // Assemble
        var request = new AccountRequest();
        request.setNome("VR Benefícios");

        // Act
        var result = ApiExec.doPost(mockMvc, API, mapToJson(request));

        // Assert
        var expected = mapToDto(result.getResponse().getContentAsString(), ErrorResponse.class);

        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        Assertions.assertThat(expected)
                .isEqualTo(new ErrorResponse("cpf is required"));
    }

    @Test
    @WithMockUser
    @DisplayName("[200] - Check balance of account successfully")
    void testCheckBalanceSuccess() throws Exception {
        // Assemble
        final var API_CHECK_BALANCE = API + "/00001";

        // Act
        var result = ApiExec.doGet(mockMvc, API_CHECK_BALANCE);

        // Assert
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo("495.15");
    }

    @Test
    @WithMockUser
    @DisplayName("[404] - Account not found to check balance")
    void testCheckBalanceWithAccountNotFound() throws Exception {
        // Assemble
        final var API_CHECK_BALANCE = API + "/00002";

        // Act
        var result = ApiExec.doGet(mockMvc, API_CHECK_BALANCE);

        // Assert
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        Assertions.assertThat(result.getResponse().getContentAsString()).isEmpty();
    }

    @Test
    @DisplayName("[401] - Authentication failed")
    void testCheckBalanceWithUnauthorized() throws Exception {
        // Assemble
        final var API_CHECK_BALANCE = API + "/00002";

        // Act
        var result = ApiExec.doGet(mockMvc, API_CHECK_BALANCE);

        // Assert
        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        Assertions.assertThat(result.getResponse().getContentAsString()).isEmpty();
    }

    private AccountResponse createAccountResponse() {
        var response = new AccountResponse();
        response.setBanco(BANK);
        response.setConta("00002");
        return response;
    }

}
