package com.angkorteam.fintech.dto.enums.loan;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum RepaymentStrategy {

    Penalties("1", "Penalties, Fees, Interest, Principal order"), 
    Overdue("4", "Overdue/Due Fee/Interest, Principal"), 
    Principal("5", "Principal, Interest, Penalties, Fees Order"), 
    Interest("6", "Interest, Principal, Penalties, Fees Order"), 
    Early("7", "Early Repayment Strategy"), 
    HeavensFamily("2", "HeavensFamily Unique"), 
    Creocore("3", "Creocore Unique");

    private String literal;

    private String description;

    RepaymentStrategy(String literal, String description) {
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

    public static RepaymentStrategy parseLiteral(String literal) {
        for (RepaymentStrategy value : RepaymentStrategy.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        RepaymentStrategy value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return new Option(value.name(), value.getDescription());
    }

}
