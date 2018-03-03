package com.angkorteam.fintech.ddl;

public interface MClientTransaction {

    public static final String NAME = "m_client_transaction";

    public interface Field {

        public static final String ID = "id";

        public static final String CLIENT_ID = "client_id";

        public static final String OFFICE_ID = "office_id";

        public static final String CURRENCY_CODE = "currency_code";

        public static final String PAYMENT_DETAIL_ID = "payment_detail_id";

        public static final String IS_REVERSED = "is_reversed";

        public static final String EXTERNAL_ID = "external_id";

        public static final String TRANSACTION_DATE = "transaction_date";

        public static final String TRANSACTION_TYPE_ENUM = "transaction_type_enum";

        public static final String AMOUNT = "amount";

        public static final String CREATED_DATE = "created_date";

        public static final String APP_USER_ID = "appuser_id";

    }

}
