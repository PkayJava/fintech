package com.angkorteam.fintech.dto.constant;

public enum JournalEntryTypeTypeEnum {
    
    Credit("1", "Credit"),
    Debit("2", "Debit");
    
    public static final String ID = "journal_entry_type_type_enum";

    private final String literal;

    private final String description;
    
    private final int enumType;

    JournalEntryTypeTypeEnum(String literal, String description) {
        this(literal, description, 0);
    }

    JournalEntryTypeTypeEnum(String literal, String description, int enumType) {
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
