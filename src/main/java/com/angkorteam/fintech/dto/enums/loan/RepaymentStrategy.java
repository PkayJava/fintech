package com.angkorteam.fintech.dto.enums.loan;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum RepaymentStrategy {

    Penalty_Fee_Interest_Principal("1", "Penalty, Fee, Interest, Principal"), 
    Overdue_DueFee_Interest_Principal("4", "Overdue, Due Fee Interest, Principal"), 
    Principal_Interest_Penalty_Fee("5", "Principal, Interest, Penalty, Fee"), 
    Interest_Principal_Penalty_Fee("6", "Interest, Principal, Penalty, Fee"), 
    Early_Repayment_Strategy("7", "Early Repayment Strategy"), 
    Heaven_Family_Unique("2", "Heaven Family Unique"), 
    Creocore_Unique("3", "Creocore Unique");

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
