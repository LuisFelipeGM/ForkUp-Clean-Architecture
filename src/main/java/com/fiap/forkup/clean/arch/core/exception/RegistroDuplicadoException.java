package com.fiap.forkup.clean.arch.core.exception;

public abstract class RegistroDuplicadoException extends RuntimeException {
    public RegistroDuplicadoException(String message) {
        super(message);
    }
}
