package com.angkorteam.fintech.client.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

public enum InterestPostingPeriod {

    Monthly("4", "Monthly"), Quarterly("5", "Quarterly"), BiAnnual("6", "BiAnnual"), Annually("7", "Annually");

    private String literal;

    private String description;

    InterestPostingPeriod(String literal, String description) {
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

    public static InterestPostingPeriod parseLiteral(String literal) {
        for (InterestPostingPeriod value : InterestPostingPeriod.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        InterestPostingPeriod value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}