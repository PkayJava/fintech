package com.angkorteam.fintech.dto.constant;

import com.angkorteam.fintech.dto.enums.AccountType;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

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

    private final Long enumType;

    private AccountType accountType;

    FinancialActivityTypeEnum(String literal, String description, AccountType accountType) {
        this(literal, description, accountType, 0l);
    }

    FinancialActivityTypeEnum(String literal, String description, AccountType accountType, Long enumType) {
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

    public Long getEnumType() {
        return enumType;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Option toOption() {
        return new Option(this.name(), this.description);
    }

    public static FinancialActivityTypeEnum parseLiteral(String literal) {
        for (FinancialActivityTypeEnum value : FinancialActivityTypeEnum.values()) {
            if (value.getLiteral().equals(literal)) {
                return value;
            }
        }
        return null;
    }

    public static Option optionLiteral(String literal) {
        FinancialActivityTypeEnum value = parseLiteral(literal);
        if (value == null) {
            return null;
        }
        return value.toOption();
    }
}
