package com.maurosagasti.api_sistema_academico.persistence.exception;

public class CarreraNotFoundException extends RuntimeException {
    public CarreraNotFoundException(String message) {
        super(message);
    }
}
