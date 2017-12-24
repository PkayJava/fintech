package com.angkorteam.fintech.dto.constant;

public enum LoanTypeEnum {
    
    Individual("1", "Individual Loan", "Individual"),
    Group("2", "Group Loan", "Group"),
    JLG("3", "Joint Liability Group Loan", "JLG");
    
    public static final String ID = "loan_type_enum";

    private final String literal;

    private final String description;
    
    private final String shortDescription;
    
    private final Long enumType;

    LoanTypeEnum(String literal, String description, String shortDescription) {
        this(literal, description, 0l, shortDescription);
    }

    LoanTypeEnum(String literal, String description, Long enumType, String shortDescription) {
        this.literal = literal;
        this.description = description;
        this.enumType = enumType;
        this.shortDescription = shortDescription;
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
    
    public String getShortDescription() {
        return shortDescription;
    }
}
