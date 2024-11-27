package br.com.fsrocha.vrcontadigital.application.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorResponse {
    String message;

    public ErrorResponse() {
    }

    public ErrorResponse(String message) {
        this.message = message;
    }
}
