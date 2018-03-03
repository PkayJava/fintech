package com.angkorteam.fintech.ddl;

public interface MShareAccountTransactions {

    public static final String NAME = "m_share_account_transactions";

    public interface Field {

        public static final String ID = "id";

        public static final String ACCOUNT_ID = "account_id";

        public static final String TRANSACTION_DATE = "transaction_date";

        public static final String TOTAL_SHARES = "total_shares";

        public static final String UNIT_PRICE = "unit_price";

        public static final String AMOUNT = "amount";

        public static final String CHARGE_AMOUNT = "charge_amount";

        public static final String AMOUNT_PAID = "amount_paid";

        public static final String STATUS_ENUM = "status_enum";

        public static final String TYPE_ENUM = "type_enum";

        public static final String IS_ACTIVE = "is_active";

    }

}
