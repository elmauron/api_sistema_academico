package com.maurosagasti.api_sistema_academico.persistence.exception;

public class ProfesorNotFoundException extends RuntimeException {
    public ProfesorNotFoundException(String message) {
        super(message);
    }
}
