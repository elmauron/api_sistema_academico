package com.maurosagasti.api_sistema_academico.controller.handler;

public class CustomApiError {
    private Integer errorCode;
    private String errorMessage;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
