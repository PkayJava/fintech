package com.angkorteam.fintech.ddl;

public interface MSavingsAccountChargePaidBy {

    public static final String NAME = "m_savings_account_charge_paid_by";

    public interface Field {

        public static final String ID = "id";

        public static final String SAVINGS_ACCOUNT_TRANSACTION_ID = "savings_account_transaction_id";

        public static final String SAVINGS_ACCOUNT_CHARGE_ID = "savings_account_charge_id";

        public static final String AMOUNT = "amount";

    }

}
