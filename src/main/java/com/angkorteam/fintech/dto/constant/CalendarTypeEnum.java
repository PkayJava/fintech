package com.angkorteam.fintech.dto.constant;

public enum CalendarTypeEnum {

    Invalid("0", "Invalid"), Clients("1", "Clients"), Groups("2", "Groups"), Loans("3", "Loans"), Centers("4", "Centers"), Savings("5", "Savings"), LoanRecalculationRestDetail("6", "Loan Recalculation Rest Detail"), LoanRecalculationCompoundingDetail("7", "Loan Recalculation Compounding Detail");

    public static final String ID = "calendar_type_enum";

    private final String literal;

    private final String description;

    private final Long enumType;

    CalendarTypeEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    CalendarTypeEnum(String literal, String description, Long enumType) {
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
