package br.com.fsrocha.vrcontadigital.application.controller;

import br.com.fsrocha.vrcontadigital.application.dto.request.TransactionRequest;
import br.com.fsrocha.vrcontadigital.domain.service.TransactionsService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contascorrente/{keyPix}")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionsController {

    TransactionsService transactionsService;

    @PostMapping("/deposito-pix")
    public ResponseEntity<Void> credit(@PathVariable String keyPix, @Valid @RequestBody TransactionRequest request) {
        transactionsService.deposit(keyPix, request.getSenha(), request.getValor());
        return ResponseEntity.ok().build();
    }

}
