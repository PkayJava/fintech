package com.angkorteam.fintech.dto;

public enum ServiceType {

    S3("1", "Amazon S3"), SMTP("2", "SMTP"), SMS("3", "SMS");

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
