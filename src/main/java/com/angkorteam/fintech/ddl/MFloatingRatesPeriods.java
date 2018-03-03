package com.angkorteam.fintech.ddl;

public interface MFloatingRatesPeriods {

    public static final String NAME = "m_floating_rates_periods";

    public interface Field {

        public static final String ID = "id";

        public static final String FLOATING_RATES_ID = "floating_rates_id";

        public static final String FROM_DATE = "from_date";

        public static final String INTEREST_RATE = "interest_rate";

        public static final String IS_DIFFERENTIAL_TO_BASE_LENDING_RATE = "is_differential_to_base_lending_rate";

        public static final String IS_ACTIVE = "is_active";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String CREATED_DATE = "created_date";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

    }

}
