package com.angkorteam.fintech.dto;

/**
 * Created by socheatkhauv on 7/13/17.
 */
public enum JournalEntry {

    Manual("1", "Manual Entries"),
    System("2", "System Entries");

    private String literal;

    private String description;

    JournalEntry(String literal, String description) {
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
