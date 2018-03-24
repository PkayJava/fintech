package com.angkorteam.fintech.dto.constant;

public enum LoanStatus {

    Invalid("0", "Invalid"), SubmittedAndAwaitingApproval("100", "Submitted And Awaiting Approval"), Approved("200", "Approved"), Active("300", "Active"), WithdrawnByClient("400", "Withdrawn By Client"), Rejected("500", "Rejected"), Closed("600", "Closed"), WrittenOff("601", "Written Off"), Rescheduled("602", "Rescheduled"), Overpaid("700", "Overpaid");

    public static final String ID = "loan_status_id";

    private final String literal;

    private final String description;

    private final Long enumType;

    LoanStatus(String literal, String description) {
        this(literal, description, 0l);
    }

    LoanStatus(String literal, String description, Long enumType) {
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
