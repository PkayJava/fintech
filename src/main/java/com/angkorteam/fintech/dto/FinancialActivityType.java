package com.angkorteam.fintech.dto;

/**
 * Created by socheatkhauv on 7/12/17.
 */
public enum FinancialActivityType {

    AssetTransfer("100", "Asset Transfer", AccountType.Asset),
    LiabilityTransfer("200", "Liability Transfer", AccountType.Liability),
    OpeningBalancesTransferContra("300", "Opening Balances Transfer Contra", AccountType.Equity),
    FundSource("103", "Fund Source", AccountType.Asset),
    PayableDividends("201", "Payable Dividends", AccountType.Liability);

    private String literal;

    private String description;

    private AccountType accountType;

    FinancialActivityType(String literal, String description, AccountType accountType) {
        this.literal = literal;
        this.description = description;
        this.accountType = accountType;
    }

    public String getLiteral() {
        return literal;
    }

    public String getDescription() {
        return description;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
