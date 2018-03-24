package com.angkorteam.fintech.dto.constant;

public enum ProcessingResultEnum {

    Invalid("0", "Invalid"), Processed("1", "Processed"), AwaitingApproval("2", "Awaiting Approval"), Rejected("3", "Rejected");

    public static final String ID = "processing_result_enum";

    private final String literal;

    private final String description;

    private final Long enumType;

    ProcessingResultEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    ProcessingResultEnum(String literal, String description, Long enumType) {
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
