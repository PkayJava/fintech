package com.angkorteam.fintech.ddl;

public interface MSavingsAccountTransactionTaxDetails {

    public static final String NAME = "m_savings_account_transaction_tax_details";

    public interface Field {

        public static final String ID = "id";

        public static final String SAVINGS_TRANSACTION_ID = "savings_transaction_id";

        public static final String TAX_COMPONENT_ID = "tax_component_id";

        public static final String AMOUNT = "amount";

    }

}
