package com.maurosagasti.api_sistema_academico.persistence.exception;

public class AlumnoNotFoundException extends RuntimeException {
    public AlumnoNotFoundException(String message) {
        super(message);
    }
}
