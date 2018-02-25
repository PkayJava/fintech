package com.angkorteam.fintech.ddl;

public interface MShareAccount {

    public static final String NAME = "m_share_account";

    public interface Field {

        public static final String ID = "id";

        public static final String ACCOUNT_NO = "account_no";

        public static final String PRODUCT_ID = "product_id";

        public static final String CLIENT_ID = "client_id";

        public static final String EXTERNAL_ID = "external_id";

        public static final String STATUS_ENUM = "status_enum";

        public static final String TOTAL_APPROVED_SHARES = "total_approved_shares";

        public static final String TOTAL_PENDING_SHARES = "total_pending_shares";

        public static final String SUBMITTED_DATE = "submitted_date";

        public static final String SUBMITTED_USER_ID = "submitted_userid";

        public static final String APPROVED_DATE = "approved_date";

        public static final String APPROVED_USER_ID = "approved_userid";

        public static final String REJECTED_DATE = "rejected_date";

        public static final String REJECTED_USER_ID = "rejected_userid";

        public static final String ACTIVATED_DATE = "activated_date";

        public static final String ACTIVATED_USER_ID = "activated_userid";

        public static final String CLOSED_DATE = "closed_date";

        public static final String CLOSED_USER_ID = "closed_userid";

        public static final String CURRENCY_CODE = "currency_code";

        public static final String CURRENCY_DIGITS = "currency_digits";

        public static final String CURRENCY_MULTIPLES_OF = "currency_multiplesof";

        public static final String SAVINGS_ACCOUNT_ID = "savings_account_id";

        public static final String MINIMUM_ACTIVE_PERIOD_FREQUENCY = "minimum_active_period_frequency";

        public static final String MINIMUM_ACTIVE_PERIOD_FREQUENCY_ENUM = "minimum_active_period_frequency_enum";

        public static final String LOCKIN_PERIOD_FREQUENCY = "lockin_period_frequency";

        public static final String LOCKIN_PERIOD_FREQUENCY_ENUM = "lockin_period_frequency_enum";

        public static final String ALLOW_DIVIDENDS_INACTIVE_CLIENTS = "allow_dividends_inactive_clients";

        public static final String CREATED_DATE = "created_date";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

    }

}
