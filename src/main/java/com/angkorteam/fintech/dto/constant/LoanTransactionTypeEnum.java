package com.angkorteam.fintech.dto.constant;

public enum LoanTransactionTypeEnum {

    Invalid("0", "Invalid"), Disbursement("1", "Disbursement"), Repayment("2", "Repayment"), Contra("3", "Contra"), WaiveInterest("4", "Waive Interest"), RepaymentAtDisbursement("5", "Repayment At Disbursement"), WriteOff("6", "Write Off"), MarkedForRescheduling("7", "Marked For Rescheduling"), RecoveryRepayment("8", "Recovery Repayment"), WaiveCharges("9", "Waive Charges"), Accrual("10", "Accrual"), InitiateTransfer("12", "Initiate Transfer"), ApproveTransfer("13", "Approve Transfer"), WithdrawTransfer("14", "Withdraw Transfer"), RejectTransfer("15", "Reject Transfer"), Refund("16", "Refund"), ChargePayment("17", "Charge Payment"), RefundForActiveLoan("18", "Refund For Active Loan"), IncomePosting("19", "Income Posting");

    public static final String ID = "loan_transaction_type_enum";

    private final String literal;

    private final String description;

    private final Long enumType;

    LoanTransactionTypeEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    LoanTransactionTypeEnum(String literal, String description, Long enumType) {
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
