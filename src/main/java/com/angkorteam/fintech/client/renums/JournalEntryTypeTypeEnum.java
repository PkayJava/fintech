package com.angkorteam.fintech.client.renums;

public enum JournalEntryTypeTypeEnum {

    Credit("1", "Credit"),
    Debit("2", "Debit");

    public static final String ID = "journal_entry_type_type_enum";

    private final String literal;

    private final String description;

    private final Long enumType;

    JournalEntryTypeTypeEnum(String literal, String description) {
        this(literal, description, 0l);
    }

    JournalEntryTypeTypeEnum(String literal, String description, Long enumType) {
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
