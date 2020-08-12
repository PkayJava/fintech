package com.angkorteam.fintech.dto.enums.loan;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

public enum ClosureInterestCalculationRule {

    PreClosureDate("1", "Calculate till pre closure date"), RestFrequencyDate("2", "Calculate till rest frequency date");

    private String literal;

    private String description;

    ClosureInterestCalculationRule(String literal, String description) {
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

    public static ClosureInterestCalculationRule parseLiteral(String literal) {
        for (ClosureInterestCalculationRule value : ClosureInterestCalculationRule.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        ClosureInterestCalculationRule value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}
