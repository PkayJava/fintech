package com.angkorteam.fintech.ddl;

public interface MSavingsAccount {

    public static final String NAME = "m_savings_account";

    public interface Field {

        public static final String ID = "id";

        public static final String ACCOUNT_NO = "account_no";

        public static final String EXTERNAL_ID = "external_id";

        public static final String CLIENT_ID = "client_id";

        public static final String GROUP_ID = "group_id";

        public static final String PRODUCT_ID = "product_id";

        public static final String FIELD_OFFICER_ID = "field_officer_id";

        public static final String STATUS_ENUM = "status_enum";

        public static final String SUB_STATUS_ENUM = "sub_status_enum";

        public static final String ACCOUNT_TYPE_ENUM = "account_type_enum";

        public static final String DEPOSIT_TYPE_ENUM = "deposit_type_enum";

        public static final String SUBMITTED_ON_DATE = "submittedon_date";

        public static final String SUBMITTED_ON_USER_ID = "submittedon_userid";

        public static final String APPROVED_ON_DATE = "approvedon_date";

        public static final String APPROVED_ON_USER_ID = "approvedon_userid";

        public static final String REJECTED_ON_DATE = "rejectedon_date";

        public static final String REJECTED_ON_USER_ID = "rejectedon_userid";

        public static final String WITHDRAWN_ON_DATE = "withdrawnon_date";

        public static final String WITHDRAWN_ON_USER_ID = "withdrawnon_userid";

        public static final String ACTIVATED_ON_DATE = "activatedon_date";

        public static final String ACTIVATED_ON_USER_ID = "activatedon_userid";

        public static final String CLOSED_ON_DATE = "closedon_date";

        public static final String CLOSED_ON_USER_ID = "closedon_userid";

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

        public static final String WITHDRAWAL_FEE_FOR_TRANSFER = "withdrawal_fee_for_transfer";

        public static final String ALLOW_OVERDRAFT = "allow_overdraft";

        public static final String OVERDRAFT_LIMIT = "overdraft_limit";

        public static final String NOMINAL_ANNUAL_INTEREST_RATE_OVERDRAFT = "nominal_annual_interest_rate_overdraft";

        public static final String MIN_OVERDRAFT_FOR_INTEREST_CALCULATION = "min_overdraft_for_interest_calculation";

        public static final String LOCKEDIN_UNTIL_DATE_DERIVED = "lockedin_until_date_derived";

        public static final String TOTAL_DEPOSITS_DERIVED = "total_deposits_derived";

        public static final String TOTAL_WITHDRAWALS_DERIVED = "total_withdrawals_derived";

        public static final String TOTAL_WITHDRAWAL_FEES_DERIVED = "total_withdrawal_fees_derived";

        public static final String TOTAL_FEES_CHARGE_DERIVED = "total_fees_charge_derived";

        public static final String TOTAL_PENALTY_CHARGE_DERIVED = "total_penalty_charge_derived";

        public static final String TOTAL_ANNUAL_FEES_DERIVED = "total_annual_fees_derived";

        public static final String TOTAL_INTEREST_EARNED_DERIVED = "total_interest_earned_derived";

        public static final String TOTAL_INTEREST_POSTED_DERIVED = "total_interest_posted_derived";

        public static final String TOTAL_OVERDRAFT_INTEREST_DERIVED = "total_overdraft_interest_derived";

        public static final String TOTAL_WITHHOLD_TAX_DERIVED = "total_withhold_tax_derived";

        public static final String ACCOUNT_BALANCE_DERIVED = "account_balance_derived";

        public static final String MIN_REQUIRED_BALANCE = "min_required_balance";

        public static final String ENFORCE_MIN_REQUIRED_BALANCE = "enforce_min_required_balance";

        public static final String MIN_BALANCE_FOR_INTEREST_CALCULATION = "min_balance_for_interest_calculation";

        public static final String START_INTEREST_CALCULATION_DATE = "start_interest_calculation_date";

        public static final String ON_HOLD_FUNDS_DERIVED = "on_hold_funds_derived";

        public static final String VERSION = "version";

        public static final String WITHHOLD_TAX = "withhold_tax";

        public static final String TAX_GROUP_ID = "tax_group_id";

        public static final String LAST_INTEREST_CALCULATION_DATE = "last_interest_calculation_date";

        public static final String TOTAL_SAVINGS_AMOUNT_ON_HOLD = "total_savings_amount_on_hold";

    }

}
