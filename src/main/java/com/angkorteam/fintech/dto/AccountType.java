package com.angkorteam.fintech.dto;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum AccountType {

    Asset("1", "Asset", "AssetAccountTags"),
    Liability("2", "Liability", "EquityAccountTags"),
    Equity("3", "Equity", "ExpenseAccountTags"),
    Income("4", "Income", "IncomeAccountTags"),
    Expense("5", "Expense", "LiabilityAccountTags");

    private String literal;

    private String description;

    private String tag;

    AccountType(String literal, String description, String tag) {
        this.literal = literal;
        this.description = description;
        this.tag = tag;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }

    public String getTag() {
        return tag;
    }
}
