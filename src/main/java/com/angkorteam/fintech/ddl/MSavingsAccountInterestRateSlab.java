package com.angkorteam.fintech.ddl;

public interface MSavingsAccountInterestRateSlab {

    public static final String NAME = "m_savings_account_interest_rate_slab";

    public interface Field {

        public static final String ID = "id";

        public static final String SAVINGS_ACCOUNT_INTEREST_RATE_CHART_ID = "savings_account_interest_rate_chart_id";

        public static final String DESCRIPTION = "description";

        public static final String PERIOD_TYPE_ENUM = "period_type_enum";

        public static final String FROM_PERIOD = "from_period";

        public static final String TO_PERIOD = "to_period";

        public static final String AMOUNT_RANGE_FROM = "amount_range_from";

        public static final String AMOUNT_RANGE_TO = "amount_range_to";

        public static final String ANNUAL_INTEREST_RATE = "annual_interest_rate";

        public static final String CURRENCY_CODE = "currency_code";

    }

}
