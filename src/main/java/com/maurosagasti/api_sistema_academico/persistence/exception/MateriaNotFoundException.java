package com.maurosagasti.api_sistema_academico.persistence.exception;

public class MateriaNotFoundException extends RuntimeException {
    public MateriaNotFoundException(String message) {
        super(message);
    }
}
