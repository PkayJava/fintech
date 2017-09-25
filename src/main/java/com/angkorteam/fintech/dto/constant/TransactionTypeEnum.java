package com.angkorteam.fintech.dto.constant;

public enum TransactionTypeEnum {
    
    Disbursement("1", "Disbursement"),
    Repayment("2", "Repayment"),
    Contra("3", "Contra"),
    WaiveInterest("4", "Waive Interest"),
    RepaymentAtDisbursement("5", "Repayment At Disbursement"),
    WriteOff("6", "Write Off"),
    MarkedForRescheduling("7", "Marked For Rescheduling"),
    RecoveryRepayment("8", "Recovery Repayment"),
    WaiveCharges("9", "Waive Charges"),
    ApplyCharges("10", "Apply Charges"),
    ApplyInterest("11", "Apply Interest");
    
    public static final String ID = "transaction_type_enum";

    private final String literal;

    private final String description;
    
    private final int enumType;

    TransactionTypeEnum(String literal, String description) {
        this(literal, description, 0);
    }

    TransactionTypeEnum(String literal, String description, int enumType) {
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
    
    public int getEnumType() {
        return enumType;
    }
}
