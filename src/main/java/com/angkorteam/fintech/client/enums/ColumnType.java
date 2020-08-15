package com.angkorteam.fintech.client.enums;

import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

/**
 * Created by socheatkhauv on 7/13/17.
 */
public enum ColumnType {

    String("String", "String"), Number("Number", "Number"), Decimal("Decimal", "Decimal"), Boolean("Boolean", "Boolean"), Date("Date", "Date"), DateTime("DateTime", "Date & Time"), Text("Text", "Text"), DropDown("DropDown", "Drop Down");

    private String literal;

    private String description;

    ColumnType(String literal, String description) {
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

    public static ColumnType parseLiteral(String literal) {
        for (ColumnType value : ColumnType.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        ColumnType value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }

}
