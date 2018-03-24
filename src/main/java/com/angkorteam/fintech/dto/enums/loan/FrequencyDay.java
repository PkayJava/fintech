package com.angkorteam.fintech.dto.enums.loan;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum FrequencyDay {

    Sunday("7", "Sunday"), Monday("1", "Monday"), Tuesday("2", "Tuesday"), Wednesday("3", "Wednesday"), Thursday("4", "Thursday"), Friday("5", "Friday"), Saturday("6", "Saturday");

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

    public Option toOption() {
        return new Option(this.name(), this.description);
    }

    public static FrequencyDay parseLiteral(String literal) {
        for (FrequencyDay value : FrequencyDay.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        FrequencyDay value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
