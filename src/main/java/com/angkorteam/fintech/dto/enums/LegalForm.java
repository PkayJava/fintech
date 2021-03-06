package com.angkorteam.fintech.dto.enums;

import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public enum LegalForm {

    Person("1", "Person"), Entity("2", "Entity");

    private String literal;

    private String description;

    LegalForm(String literal, String description) {
        this.literal = literal;
        this.description = description;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }

    public Option toOption() {
        return new Option(this.name(), this.description);
    }

    public static LegalForm parseLiteral(String literal) {
        for (LegalForm value : LegalForm.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        LegalForm value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}
