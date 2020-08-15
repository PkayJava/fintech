package com.angkorteam.fintech.client.enums.loan;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

public enum AdvancePaymentsAdjustmentType {

    ReduceEMIAmount("3", "Reduce EMI amount"), ReduceNumberOfInstallments("2", "Reduce number of installments"), RescheduleNextRepayments("1", "Reschedule next repayments");

    private String literal;

    private String description;

    AdvancePaymentsAdjustmentType(String literal, String description) {
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

    public static AdvancePaymentsAdjustmentType parseLiteral(String literal) {
        for (AdvancePaymentsAdjustmentType value : AdvancePaymentsAdjustmentType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        AdvancePaymentsAdjustmentType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
