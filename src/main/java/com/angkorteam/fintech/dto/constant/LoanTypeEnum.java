package com.angkorteam.fintech.dto.constant;

public enum LoanTypeEnum {
    
    Individual("1", "Individual Loan"),
    Group("2", "Group Loan");
    
    public static final String ID = "loan_type_enum";

    private final String literal;

    private final String description;
    
    private final int enumType;

    LoanTypeEnum(String literal, String description) {
        this(literal, description, 0);
    }

    LoanTypeEnum(String literal, String description, int enumType) {
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
