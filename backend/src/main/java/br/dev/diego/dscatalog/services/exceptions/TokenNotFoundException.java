package br.dev.diego.dscatalog.services.exceptions;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(String message) {
        super(message);
    }
}
