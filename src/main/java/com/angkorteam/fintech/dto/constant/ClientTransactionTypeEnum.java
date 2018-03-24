package com.angkorteam.fintech.dto.constant;

public enum ClientTransactionTypeEnum {

    PayCharge("1", "Pay Charge"), WaiveCharge("2      ", "Waive Charge");

    public static final String ID = "client_transaction_type_enum";

    private final String literal;

    private final String description;

    private final Long enumType;

    ClientTransactionTypeEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    ClientTransactionTypeEnum(String literal, String description, Long enumType) {
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
