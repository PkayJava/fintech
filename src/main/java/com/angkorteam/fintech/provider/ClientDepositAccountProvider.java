package com.angkorteam.fintech.provider;

public class ClientDepositAccountProvider extends SingleChoiceProvider {

    public ClientDepositAccountProvider() {
        super("m_savings_account", "m_savings_account.id", "m_savings_account.account_no", "m_savings_account.account_no", "concat(m_savings_account.account_no, ' [', m_savings_product.short_name, ']')");
        applyJoin("m_savings_product", "inner join m_savings_product on m_savings_account.product_id = m_savings_product.id");
    }

}