package com.angkorteam.fintech.dto;

public enum ChargeTime {

    Disbursement("1", "Disbursement"), 
    SpecifiedDueDate("2", "Specified Due Date"), 
    SavingsActivation("3", "Savings Activation"), 
    WithdrawalFee("5", "Withdrawal Fee"), 
    AnnualFee("6", "Annual Fee"), 
    MonthlyFee("7", "Monthly Fee"), 
    WeeklyFee("11", "Weekly Fee"), 
    OverdraftFee("10", "Overdraft Fee"), 
    SavingNoActivityFee("16", "Saving No Activity Fee"), 
    InstallmentFee("8", "Installment Fee"), 
    OverdueFees("9", "Overdue Fees"), 
    TrancheDisbursement("12", "Tranche Disbursement"), 
    ShareAccountActivate("13", "Share Account Activate"), 
    SharePurchase("14", "Share Purchase"), 
    ShareRedeem("15", "Share Redeem");

    private String literal;

    private String description;

    ChargeTime(String literal, String description) {
        this.literal = literal;
        this.description = description;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }

}
