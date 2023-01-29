package br.dev.diego.dscatalog.services.exceptions;

public class TokenVerifyException extends RuntimeException {

    public TokenVerifyException(String message) {
        super(message);
    }
}
