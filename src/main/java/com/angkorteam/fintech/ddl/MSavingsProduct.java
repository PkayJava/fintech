package com.angkorteam.fintech.ddl;

public interface MSavingsProduct {

    public static final String NAME = "m_savings_product";

    public interface Field {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String SHORT_NAME = "short_name";

        public static final String DESCRIPTION = "description";

        public static final String DEPOSIT_TYPE_ENUM = "deposit_type_enum";

        public static final String CURRENCY_CODE = "currency_code";

        public static final String CURRENCY_DIGITS = "currency_digits";

        public static final String CURRENCY_MULTIPLES_OF = "currency_multiplesof";

        public static final String NOMINAL_ANNUAL_INTEREST_RATE = "nominal_annual_interest_rate";

        public static final String INTEREST_COMPOUNDING_PERIOD_ENUM = "interest_compounding_period_enum";

        public static final String INTEREST_POSTING_PERIOD_ENUM = "interest_posting_period_enum";

        public static final String INTEREST_CALCULATION_TYPE_ENUM = "interest_calculation_type_enum";

        public static final String INTEREST_CALCULATION_DAYS_IN_YEAR_TYPE_ENUM = "interest_calculation_days_in_year_type_enum";

        public static final String MIN_REQUIRED_OPENING_BALANCE = "min_required_opening_balance";

        public static final String LOCKIN_PERIOD_FREQUENCY = "lockin_period_frequency";

        public static final String LOCKIN_PERIOD_FREQUENCY_ENUM = "lockin_period_frequency_enum";

        public static final String ACCOUNTING_TYPE = "accounting_type";

        public static final String WITHDRAWAL_FEE_AMOUNT = "withdrawal_fee_amount";

        public static final String WITHDRAWAL_FEE_TYPE_ENUM = "withdrawal_fee_type_enum";

        public static final String WITHDRAWAL_FEE_FOR_TRANSFER = "withdrawal_fee_for_transfer";

        public static final String ALLOW_OVERDRAFT = "allow_overdraft";

        public static final String OVERDRAFT_LIMIT = "overdraft_limit";

        public static final String NOMINAL_ANNUAL_INTEREST_RATE_OVERDRAFT = "nominal_annual_interest_rate_overdraft";

        public static final String MIN_OVERDRAFT_FOR_INTEREST_CALCULATION = "min_overdraft_for_interest_calculation";

        public static final String MIN_REQUIRED_BALANCE = "min_required_balance";

        public static final String ENFORCE_MIN_REQUIRED_BALANCE = "enforce_min_required_balance";

        public static final String MIN_BALANCE_FOR_INTEREST_CALCULATION = "min_balance_for_interest_calculation";

        public static final String WITHHOLD_TAX = "withhold_tax";

        public static final String TAX_GROUP_ID = "tax_group_id";

        public static final String IS_DORMANCY_TRACKING_ACTIVE = "is_dormancy_tracking_active";

        public static final String DAYS_TO_INACTIVE = "days_to_inactive";

        public static final String DAYS_TO_DORMANCY = "days_to_dormancy";

        public static final String DAYS_TO_ESCHEAT = "days_to_escheat";

    }

}
