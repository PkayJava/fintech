package com.angkorteam.fintech.dto;

/**
 * Created by socheatkhauv on 7/13/17.
 */
public enum ColumnType {

    String("String", "String"),
    Number("Number", "Number"),
    Decimal("Decimal", "Decimal"),
    Boolean("Boolean", "Boolean"),
    Date("Date", "Date"),
    DateTime("DateTime", "Date & Time"),
    Text("Text", "Text"),
    DropDown("DropDown", "Drop Down");

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

}
