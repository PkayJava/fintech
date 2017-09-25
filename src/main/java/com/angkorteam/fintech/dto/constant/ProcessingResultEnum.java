package com.angkorteam.fintech.dto.constant;

public enum ProcessingResultEnum {
    
    Invalid("0", "Invalid"),
    Processed("1", "Processed"),
    AwaitingApproval("2", "Awaiting Approval"),
    Rejected("3", "Rejected");
    
    public static final String ID = "processing_result_enum";

    private final String literal;

    private final String description;
    
    private final int enumType;
    
    ProcessingResultEnum(String literal, String description) {
        this(literal, description, 0);
    }

    ProcessingResultEnum(String literal, String description, int enumType) {
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
