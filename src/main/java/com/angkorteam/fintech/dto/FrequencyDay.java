package com.angkorteam.fintech.dto;

public enum FrequencyDay {

    Day("Day", "Day"), 
    Sunday("Sunday", "Sunday"),
    Monday("Monday", "Monday"),
    Tuesday("Tuesday", "Tuesday"),
    Wednesday("Wednesday", "Wednesday"),
    Thursday("Thursday", "Thursday"),
    Friday("Friday", "Friday"),
    Saturday("Saturday", "Saturday");

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
