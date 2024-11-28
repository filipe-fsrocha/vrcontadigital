package br.com.fsrocha.vrcontadigital.application.controller;

import br.com.fsrocha.vrcontadigital.application.dto.request.TransactionRequest;
import br.com.fsrocha.vrcontadigital.application.dto.response.DebitAccountResponse;
import br.com.fsrocha.vrcontadigital.application.mapper.TransactionMapper;
import br.com.fsrocha.vrcontadigital.domain.enums.OperationType;
import br.com.fsrocha.vrcontadigital.domain.service.TransactionFactory;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("SpellCheckingInspection")
@RestController
@RequiredArgsConstructor
@RequestMapping("/contascorrente/{keyPix}")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionsController {

    TransactionFactory transactionFactory;

    @PostMapping("/deposito-pix")
    public ResponseEntity<Void> credit(@PathVariable String keyPix, @Valid @RequestBody TransactionRequest request) {
        var transactionData = TransactionMapper.toTransactionData(keyPix, request, OperationType.CREDIT);
        transactionFactory.getTransactionExecutor(transactionData.getOperationType()).execute(transactionData);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/saque")
    public ResponseEntity<DebitAccountResponse> debit(@PathVariable String keyPix, @Valid @RequestBody TransactionRequest request) {
        var transactionData = TransactionMapper.toTransactionData(keyPix, request, OperationType.DEBIT);
        var result = transactionFactory.getTransactionExecutor(OperationType.DEBIT).execute(transactionData);
        return ResponseEntity.ok(new DebitAccountResponse(result.getCurrentAmount()));
    }

}
