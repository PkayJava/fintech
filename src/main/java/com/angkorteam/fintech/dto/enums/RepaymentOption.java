package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/13/17.
 */
public enum RepaymentOption {

    SameDay("1", "Same Day"),
    MoveToNextWorkingDay("2", "Move To Next Working Day"),
    MoveToNextRepaymentMeetingDay("3", "Move To Next Repayment Meeting Day"),
    MoveToPreviousWorkingDay("4", "Move To Previous Working Day");

    private String literal;

    private String description;

    RepaymentOption(String literal, String description) {
        this.literal = literal;
        this.description = description;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }
    
    public static RepaymentOption parseLiteral(String literal) {
        for (RepaymentOption value : RepaymentOption.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }
    
    public static Option optionLiteral(String literal) {
        RepaymentOption value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return new Option(value.name(), value.getDescription());
    }

}
