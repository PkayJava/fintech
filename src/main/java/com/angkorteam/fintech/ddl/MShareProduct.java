package com.angkorteam.fintech.ddl;

public interface MShareProduct {

    public static final String NAME = "m_share_product";

    public interface Field {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String SHORT_NAME = "short_name";

        public static final String EXTERNAL_ID = "external_id";

        public static final String DESCRIPTION = "description";

        public static final String START_DATE = "start_date";

        public static final String END_DATE = "end_date";

        public static final String CURRENCY_CODE = "currency_code";

        public static final String CURRENCY_DIGITS = "currency_digits";

        public static final String CURRENCY_MULTIPLES_OF = "currency_multiplesof";

        public static final String TOTAL_SHARES = "total_shares";

        public static final String ISSUED_SHARES = "issued_shares";

        public static final String TOTALSUBSCRIBED_SHARES = "totalsubscribed_shares";

        public static final String UNIT_PRICE = "unit_price";

        public static final String CAPITAL_AMOUNT = "capital_amount";

        public static final String MINIMUM_CLIENT_SHARES = "minimum_client_shares";

        public static final String NOMINAL_CLIENT_SHARES = "nominal_client_shares";

        public static final String MAXIMUM_CLIENT_SHARES = "maximum_client_shares";

        public static final String MINIMUM_ACTIVE_PERIOD_FREQUENCY = "minimum_active_period_frequency";

        public static final String MINIMUM_ACTIVE_PERIOD_FREQUENCY_ENUM = "minimum_active_period_frequency_enum";

        public static final String LOCKIN_PERIOD_FREQUENCY = "lockin_period_frequency";

        public static final String LOCKIN_PERIOD_FREQUENCY_ENUM = "lockin_period_frequency_enum";

        public static final String ALLOW_DIVIDENDS_INACTIVE_CLIENTS = "allow_dividends_inactive_clients";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String CREATED_DATE = "created_date";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

        public static final String ACCOUNTING_TYPE = "accounting_type";

    }

}
