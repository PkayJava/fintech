package com.angkorteam.fintech.ddl;

public interface MSavingsAccountInterestRateChart {

    public static final String NAME = "m_savings_account_interest_rate_chart";

    public interface Field {

        public static final String ID = "id";

        public static final String SAVINGS_ACCOUNT_ID = "savings_account_id";

        public static final String NAME = "name";

        public static final String DESCRIPTION = "description";

        public static final String FROM_DATE = "from_date";

        public static final String END_DATE = "end_date";

        public static final String IS_PRIMARY_GROUPING_BY_AMOUNT = "is_primary_grouping_by_amount";

    }

}
