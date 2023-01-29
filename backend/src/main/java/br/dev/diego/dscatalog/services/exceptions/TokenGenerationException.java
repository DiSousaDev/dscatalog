package br.dev.diego.dscatalog.services.exceptions;

public class TokenGenerationException extends RuntimeException {

    public TokenGenerationException(String message) {
        super(message);
    }
}
