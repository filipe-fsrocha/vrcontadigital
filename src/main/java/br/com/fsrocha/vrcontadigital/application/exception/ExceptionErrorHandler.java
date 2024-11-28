package br.com.fsrocha.vrcontadigital.application.exception;

import br.com.fsrocha.vrcontadigital.domain.enums.TransactionStatus;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {AccountExistsException.class})
    public ResponseEntity<Object> accountExistsException() {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> notFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(value = {AccountNotFoundException.class})
    public ResponseEntity<Object> accountNotFound() {
        return ResponseEntity.unprocessableEntity().body(TransactionStatus.ACCOUNT_NOT_FOUND.getTranslate());
    }

    @ExceptionHandler(value = {InsufficientFundsException.class})
    public ResponseEntity<Object> insufficientFunds() {
        return ResponseEntity.unprocessableEntity().body(TransactionStatus.INSUFFICIENT_FUNDS.getTranslate());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        return ResponseEntity.badRequest().build();
    }
}
