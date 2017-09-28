package com.angkorteam.fintech.dto;

public enum ReschedulingType {
    
    NextRepaymentDate("1", "Reschedule to next repayment date"), 
    SpecifiedDate("2", "Reschedule to specified date");

    private String literal;

    private String description;

    ReschedulingType(String literal, String description) {
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
