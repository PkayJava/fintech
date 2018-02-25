package com.angkorteam.fintech.ddl;

public interface MCashierTransactions {

    public static final String NAME = "m_cashier_transactions";

    public interface Field {

        public static final String ID = "id";

        public static final String CASHIER_ID = "cashier_id";

        public static final String TXN_TYPE = "txn_type";

        public static final String TXN_AMOUNT = "txn_amount";

        public static final String TXN_DATE = "txn_date";

        public static final String CREATED_DATE = "created_date";

        public static final String ENTITY_TYPE = "entity_type";

        public static final String ENTITY_ID = "entity_id";

        public static final String TXN_NOTE = "txn_note";

        public static final String CURRENCY_CODE = "currency_code";

    }

}
