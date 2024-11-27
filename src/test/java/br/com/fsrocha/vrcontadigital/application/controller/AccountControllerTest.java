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
    @DisplayName("[401] - Authentication error")
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
        request.setCpf("11111111111");
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
    @DisplayName("[400] - Create accout wiht invalida payload")
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

    private AccountResponse createAccountResponse() {
        var response = new AccountResponse();
        response.setBanco(BANK);
        response.setConta("00001");
        return response;
    }

}
