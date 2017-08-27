package com.angkorteam.fintech.dto.loan;

public enum FrequencyDay {

    Sunday("7", "Sunday"),
    Monday("1", "Monday"),
    Tuesday("2", "Tuesday"),
    Wednesday("3", "Wednesday"),
    Thursday("4", "Thursday"),
    Friday("5", "Friday"),
    Saturday("6", "Saturday");

    private String literal;

    private String description;

    FrequencyDay(String literal, String description) {
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
