package com.angkorteam.fintech.client.dto;


public class AppException extends RuntimeException {

    private final String text;

    private final AppError object;

    public AppException(String text, AppError object) {
        this.text = text;
        this.object = object;
    }

    public AppError getObject() {
        return this.object;
    }

    public String getText() {
        return this.text;
    }

}
