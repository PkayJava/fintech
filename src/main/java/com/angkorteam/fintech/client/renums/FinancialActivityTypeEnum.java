package com.angkorteam.fintech.client.renums;

import com.angkorteam.fintech.client.enums.GLAccountType;
import com.angkorteam.webui.frmk.wicket.markup.html.form.select2.Option;

public enum FinancialActivityTypeEnum {

    AssetTransfer("100", "Asset Transfer", GLAccountType.Asset),
    CashAtMainVault("101", "Main Cash Account or Cash at Vault", GLAccountType.Asset),
    CashAtTeller("102", "Cash at Tellers/Cashiers", GLAccountType.Asset),
    AssetFundSource("103", "Fund Source", GLAccountType.Asset),
    LiabilityTransfer("200", "Liability Transfer", GLAccountType.Liability),
    PayableDividends("201", "Payable Dividends", GLAccountType.Liability),
    OpeningBalancesTransferContra("300", "Opening Balances Transfer Contra", GLAccountType.Equity);

    public static final String ID = "financial_activity_type_enum";

    private final String literal;

    private final String description;

    private final Long enumType;

    private GLAccountType accountType;

    FinancialActivityTypeEnum(String literal, String description, GLAccountType accountType) {
        this(literal, description, accountType, 0l);
    }

    FinancialActivityTypeEnum(String literal, String description, GLAccountType accountType, Long enumType) {
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

    public GLAccountType getAccountType() {
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
