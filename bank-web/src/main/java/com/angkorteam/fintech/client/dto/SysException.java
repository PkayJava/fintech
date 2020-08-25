package com.angkorteam.fintech.client.dto;

public class SysException extends RuntimeException {

    private final String text;

    private final SysError object;

    public SysException(String text, SysError object) {
        this.text = text;
        this.object = object;
    }

    public SysError getObject() {
        return this.object;
    }

    public String getText() {
        return this.text;
    }

}
