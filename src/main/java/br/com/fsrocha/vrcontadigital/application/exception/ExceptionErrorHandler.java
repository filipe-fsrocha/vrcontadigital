package br.com.fsrocha.vrcontadigital.application.exception;

import br.com.fsrocha.vrcontadigital.application.dto.response.ErrorResponse;
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

import java.util.Objects;

@ControllerAdvice
public class ExceptionErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {AccountExistsException.class})
    public ResponseEntity<Object> accountExistsException() {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        var errorMessage = Objects.requireNonNull(Objects.requireNonNull(ex)
                .getBindingResult().getFieldError()).getDefaultMessage();
        return new ResponseEntity<>(new ErrorResponse(errorMessage), headers, status);
    }
}
