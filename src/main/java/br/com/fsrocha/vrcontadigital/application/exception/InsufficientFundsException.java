package br.com.fsrocha.vrcontadigital.application.exception;

import java.io.Serial;

public class InsufficientFundsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InsufficientFundsException() {
    }

    public InsufficientFundsException(String message) {
        super(message);
    }
}
