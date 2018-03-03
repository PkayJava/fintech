package com.angkorteam.fintech.ddl;

public interface MPaymentDetail {

    public static final String NAME = "m_payment_detail";

    public interface Field {

        public static final String ID = "id";

        public static final String PAYMENT_TYPE_ID = "payment_type_id";

        public static final String ACCOUNT_NUMBER = "account_number";

        public static final String CHECK_NUMBER = "check_number";

        public static final String RECEIPT_NUMBER = "receipt_number";

        public static final String BANK_NUMBER = "bank_number";

        public static final String ROUTING_CODE = "routing_code";

    }

}
