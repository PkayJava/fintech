package com.angkorteam.fintech.dto.enums.loan;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

public enum NominalInterestRateType {

    Month("2", "Per month"), Year("3", "Per year");

    private String literal;

    private String description;

    NominalInterestRateType(String literal, String description) {
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

    public static NominalInterestRateType parseLiteral(String literal) {
        for (NominalInterestRateType value : NominalInterestRateType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        NominalInterestRateType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}
