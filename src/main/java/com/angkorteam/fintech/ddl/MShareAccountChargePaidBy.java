package com.angkorteam.fintech.ddl;

public interface MShareAccountChargePaidBy {

    public static final String NAME = "m_share_account_charge_paid_by";

    public interface Field {

        public static final String ID = "id";

        public static final String SHARE_TRANSACTION_ID = "share_transaction_id";

        public static final String CHARGE_TRANSACTION_ID = "charge_transaction_id";

        public static final String AMOUNT = "amount";

    }

}
