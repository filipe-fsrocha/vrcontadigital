package br.com.fsrocha.vrcontadigital.application.exception;

import java.io.Serial;

public class AccountNotFoundException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AccountNotFoundException() {
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
