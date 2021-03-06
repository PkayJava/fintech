package com.angkorteam.fintech.dto.constant;

public enum SavingsTransactionTypeEnum {

    Invalid("0", "Invalid"), Deposit("1", "Deposit"), Withdrawal("2", "Withdrawal", 1l), InterestPosting("3", "Interest Posting"), WithdrawalFee("4", "Withdrawal Fee", 1l), AnnualFee("5", "Annual Fee", 1l), WaiveCharge("6", "Waive Charge"), PayCharge("7", "Pay Charge", 1l), DividendPayout("8", "Dividend Payout"), InitiateTransfer("12", "Initiate Transfer"), ApproveTransfer("13", "Approve Transfer"), WithdrawTransfer("14", "Withdraw Transfer"), RejectTransfer("15", "Reject Transfer"), WrittenOff("16", "Written Off"), OverdraftInterest("17", "Overdraft Interest"), WithHoldTax("19", "WithHold Tax");

    public static final String ID = "savings_transaction_type_enum";

    private final String literal;

    private final String description;

    private final Long enumType;

    SavingsTransactionTypeEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    SavingsTransactionTypeEnum(String literal, String description, Long enumType) {
        this.literal = literal;
        this.description = description;
        this.enumType = enumType;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }

    public Long getEnumType() {
        return enumType;
    }

}
