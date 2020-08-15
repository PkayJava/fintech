package com.angkorteam.fintech.client.renums;

public enum LoanTransactionStrategy {

    MifosStandardStrategy("1", "Mifos Standard Strategy"),
    HeavensFamilyStrategy("2", "Heavens Family Strategy"),
    CreocoreStrategy("3", "Creocore Strategy"),
    RbiIndiaStrategy("4", "RBI India Strategy");

    public static final String ID = "loan_transaction_strategy_id";

    private final String literal;

    private final String description;

    private final Long enumType;

    LoanTransactionStrategy(String literal, String description) {
        this(literal, description, 0l);
    }

    LoanTransactionStrategy(String literal, String description, Long enumType) {
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
