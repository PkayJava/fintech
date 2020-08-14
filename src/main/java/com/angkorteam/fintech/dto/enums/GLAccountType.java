package com.angkorteam.fintech.dto.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum GLAccountType {

    Asset("1", "Asset", "AssetAccountTags"),
    Liability("2", "Liability", "LiabilityAccountTags"),
    Equity("3", "Equity", "EquityAccountTags"),
    Income("4", "Income", "IncomeAccountTags"),
    Expense("5", "Expense", "ExpenseAccountTags");

    private String literal;

    private String description;

    private String tag;

    GLAccountType(String literal, String description, String tag) {
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

    public Option toOption() {
        return new Option(this.name(), this.description);
    }

    public static GLAccountType parseLiteral(String literal) {
        for (GLAccountType value : GLAccountType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        GLAccountType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
