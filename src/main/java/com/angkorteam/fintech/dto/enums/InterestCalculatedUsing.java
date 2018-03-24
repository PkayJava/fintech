package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum InterestCalculatedUsing {

    DailyBalance("1", "Daily Balance"), AverageDailyBalance("2", "Average Daily Balance");

    private String literal;

    private String description;

    InterestCalculatedUsing(String literal, String description) {
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

    public static InterestCalculatedUsing parseLiteral(String literal) {
        for (InterestCalculatedUsing value : InterestCalculatedUsing.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        InterestCalculatedUsing value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
