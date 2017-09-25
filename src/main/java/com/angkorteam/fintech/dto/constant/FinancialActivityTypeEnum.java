package com.angkorteam.fintech.dto.constant;

public enum FinancialActivityTypeEnum {

    AssetTransfer("100", "Asset Transfer"),
    CashAtMainvault("101", "Cash At Mainvault"),
    CashAtTeller("102", "Cash At Teller"),
    AssetFundSource("103", "Asset Fund Source"),
    LiabilityTransfer("200", "Liability Transfer"),
    PayableDividends("201", "Payable Dividends"),
    OpeningBalancesTransferContra("300", "Opening Balances Transfer Contra");
    
    public static final String ID = "financial_activity_type_enum";

    private final String literal;

    private final String description;
    
    private final int enumType;

    FinancialActivityTypeEnum(String literal, String description) {
        this(literal, description, 0);
    }

    FinancialActivityTypeEnum(String literal, String description, int enumType) {
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
