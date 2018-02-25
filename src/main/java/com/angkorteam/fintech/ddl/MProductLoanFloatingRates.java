package com.angkorteam.fintech.ddl;

public interface MProductLoanFloatingRates {

    public static final String NAME = "m_product_loan_floating_rates";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_PRODUCT_ID = "loan_product_id";

        public static final String FLOATING_RATES_ID = "floating_rates_id";

        public static final String INTEREST_RATE_DIFFERENTIAL = "interest_rate_differential";

        public static final String MIN_DIFFERENTIAL_LENDING_RATE = "min_differential_lending_rate";

        public static final String DEFAULT_DIFFERENTIAL_LENDING_RATE = "default_differential_lending_rate";

        public static final String MAX_DIFFERENTIAL_LENDING_RATE = "max_differential_lending_rate";

        public static final String IS_FLOATING_INTEREST_RATE_CALCULATION_ALLOWED = "is_floating_interest_rate_calculation_allowed";

    }

}
