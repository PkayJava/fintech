package com.angkorteam.fintech.dto.constant;

public enum AmortizationMethodEnum {
    
    PrinciplePayments("0", "Equal Principle Payments"),
    Installments("1", "Equal Installments");
    
    public static final String ID = "amortization_method_enum";

    private final String literal;

    private final String description;
    
    private final int enumType;

    AmortizationMethodEnum(String literal, String description) {
        this(literal, description, 0);
    }

    AmortizationMethodEnum(String literal, String description, int enumType) {
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
