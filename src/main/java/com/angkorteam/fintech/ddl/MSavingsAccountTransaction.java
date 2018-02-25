package com.angkorteam.fintech.ddl;

public interface MSavingsAccountTransaction {

    public static final String NAME = "m_savings_account_transaction";

    public interface Field {

        public static final String ID = "id";

        public static final String SAVINGS_ACCOUNT_ID = "savings_account_id";

        public static final String OFFICE_ID = "office_id";

        public static final String PAYMENT_DETAIL_ID = "payment_detail_id";

        public static final String TRANSACTION_TYPE_ENUM = "transaction_type_enum";

        public static final String IS_REVERSED = "is_reversed";

        public static final String TRANSACTION_DATE = "transaction_date";

        public static final String AMOUNT = "amount";

        public static final String OVERDRAFT_AMOUNT_DERIVED = "overdraft_amount_derived";

        public static final String BALANCE_END_DATE_DERIVED = "balance_end_date_derived";

        public static final String BALANCE_NUMBER_OF_DAYS_DERIVED = "balance_number_of_days_derived";

        public static final String RUNNING_BALANCE_DERIVED = "running_balance_derived";

        public static final String CUMULATIVE_BALANCE_DERIVED = "cumulative_balance_derived";

        public static final String CREATED_DATE = "created_date";

        public static final String APP_USER_ID = "appuser_id";

        public static final String IS_MANUAL = "is_manual";

        public static final String RELEASE_ID_OF_HOLD_AMOUNT = "release_id_of_hold_amount";

    }

}
