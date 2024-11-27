package br.com.fsrocha.vrcontadigital.application.exception;

import java.io.Serial;

public class AccountExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AccountExistsException() {
    }

    public AccountExistsException(String message) {
        super(message);
    }
}
