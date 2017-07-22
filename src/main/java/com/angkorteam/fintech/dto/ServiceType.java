package com.angkorteam.fintech.dto;

public enum ServiceType {

    S3("S3", "Amazon S3"), SMTP("SMTP", "Email Service");

    private String literal;

    private String description;

    ServiceType(String literal, String description) {
        this.literal = literal;
        this.description = description;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }

}
