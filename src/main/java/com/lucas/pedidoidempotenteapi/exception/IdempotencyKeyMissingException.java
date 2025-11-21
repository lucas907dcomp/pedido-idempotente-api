package com.lucas.pedidoidempotenteapi.exception;

public class IdempotencyKeyMissingException extends RuntimeException {
    public IdempotencyKeyMissingException() {
        super("Header 'Idempotency-Key' é obrigatório.");
    }
}
