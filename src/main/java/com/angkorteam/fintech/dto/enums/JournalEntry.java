package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/13/17.
 */
public enum JournalEntry {

    Manual("1", "Manual Entries"), System("2", "System Entries");

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

    public Option toOption() {
        return new Option(this.name(), this.description);
    }

    public static JournalEntry parseLiteral(String literal) {
        for (JournalEntry value : JournalEntry.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        JournalEntry value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
