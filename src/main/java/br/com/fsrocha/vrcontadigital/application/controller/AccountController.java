package br.com.fsrocha.vrcontadigital.application.controller;

import br.com.fsrocha.vrcontadigital.application.dto.request.AccountRequest;
import br.com.fsrocha.vrcontadigital.application.dto.response.AccountResponse;
import br.com.fsrocha.vrcontadigital.application.mapper.AccountMapper;
import br.com.fsrocha.vrcontadigital.domain.service.AccountService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contascorrente")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountController {

    AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody AccountRequest request) {
        var accountEntity = accountService.createAccount(AccountMapper.toEntity(request));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(AccountMapper.toResponse(accountEntity));
    }

}
