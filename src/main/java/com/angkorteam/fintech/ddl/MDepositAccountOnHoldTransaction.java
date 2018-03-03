package com.angkorteam.fintech.ddl;

public interface MDepositAccountOnHoldTransaction {

    public static final String NAME = "m_deposit_account_on_hold_transaction";

    public interface Field {

        public static final String ID = "id";

        public static final String SAVINGS_ACCOUNT_ID = "savings_account_id";

        public static final String AMOUNT = "amount";

        public static final String TRANSACTION_TYPE_ENUM = "transaction_type_enum";

        public static final String TRANSACTION_DATE = "transaction_date";

        public static final String IS_REVERSED = "is_reversed";

        public static final String CREATED_DATE = "created_date";

    }

}
