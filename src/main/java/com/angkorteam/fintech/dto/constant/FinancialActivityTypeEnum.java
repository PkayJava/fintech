package com.angkorteam.fintech.dto.constant;

import com.angkorteam.fintech.dto.enums.AccountType;

public enum FinancialActivityTypeEnum {

    AssetTransfer("100", "Asset Transfer", AccountType.Asset),
    CashAtMainvault("101", "Cash At Mainvault", AccountType.Asset),
    CashAtTeller("102", "Cash At Teller", AccountType.Asset),
    AssetFundSource("103", "Asset Fund Source", AccountType.Asset),
    LiabilityTransfer("200", "Liability Transfer", AccountType.Liability),
    PayableDividends("201", "Payable Dividends", AccountType.Liability),
    OpeningBalancesTransferContra("300", "Opening Balances Transfer Contra", AccountType.Equity);
    
    public static final String ID = "financial_activity_type_enum";

    private final String literal;

    private final String description;
    
    private final int enumType;
    
    private AccountType accountType;

    FinancialActivityTypeEnum(String literal, String description, AccountType accountType) {
        this(literal, description, accountType, 0);
    }

    FinancialActivityTypeEnum(String literal, String description, AccountType accountType, int enumType) {
        this.literal = literal;
        this.description = description;
        this.enumType = enumType;
        this.accountType = accountType;
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
    
    public AccountType getAccountType() {
        return accountType;
    }
}
