package com.angkorteam.fintech.client.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

/**
 * charge_time_enum
 * 
 * @author socheat
 *
 */
public enum ChargeTimeType {

    Invalid("0", "Invalid"),
    Disbursement("1", "Disbursement"),
    SpecifiedDueDate("2", "Specified Due Date"),
    SavingActivation("3", "Savings Activation"),
    SavingClosure("4", "Savings Activation"),
    WithdrawalFee("5", "Withdrawal Fee"),
    AnnualFee("6", "Annual Fee"),
    MonthlyFee("7", "Monthly Fee"),
    InstallmentFee("8", "Installment Fee"),
    OverdueInstallment("9", "Overdue Installment"),
    OverdraftFee("10", "Overdraft Fee"),
    WeeklyFee("11", "Weekly Fee"),
    TrancheDisbursement("12", "Tranche Disbursement"),

    ShareAccountActivate("13", "Share Account Activate"),
    SharePurchase("14", "Share Purchase"),
    ShareRedeem("15", "Share Redeem"),

    SavingNoActivityFee("16", "Saving No Activity Fee");

    private String literal;

    private String description;

    ChargeTimeType(String literal, String description) {
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

    public static ChargeTimeType parseLiteral(String literal) {
        for (ChargeTimeType value : ChargeTimeType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        ChargeTimeType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
