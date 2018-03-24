package com.angkorteam.fintech.dto.constant;

public enum AmortizationMethodEnum {

    PrinciplePayments("0", "Equal Principle Payments"), Installments("1", "Equal Installments");

    public static final String ID = "amortization_method_enum";

    private final String literal;

    private final String description;

    private final Long enumType;

    AmortizationMethodEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    AmortizationMethodEnum(String literal, String description, Long enumType) {
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
