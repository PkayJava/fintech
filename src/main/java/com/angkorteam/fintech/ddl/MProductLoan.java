package com.angkorteam.fintech.ddl;

public interface MProductLoan {

    public static final String NAME = "m_product_loan";

    public interface Field {

        public static final String ID = "id";

        public static final String SHORT_NAME = "short_name";

        public static final String CURRENCY_CODE = "currency_code";

        public static final String CURRENCY_DIGITS = "currency_digits";

        public static final String CURRENCY_MULTIPLES_OF = "currency_multiplesof";

        public static final String PRINCIPAL_AMOUNT = "principal_amount";

        public static final String MIN_PRINCIPAL_AMOUNT = "min_principal_amount";

        public static final String MAX_PRINCIPAL_AMOUNT = "max_principal_amount";

        public static final String ARREARS_TOLERANCE_AMOUNT = "arrearstolerance_amount";

        public static final String NAME = "name";

        public static final String DESCRIPTION = "description";

        public static final String FUND_ID = "fund_id";

        public static final String IS_LINKED_TO_FLOATING_INTEREST_RATES = "is_linked_to_floating_interest_rates";

        public static final String ALLOW_VARIABE_INSTALLMENTS = "allow_variabe_installments";

        public static final String NOMINAL_INTEREST_RATE_PER_PERIOD = "nominal_interest_rate_per_period";

        public static final String MIN_NOMINAL_INTEREST_RATE_PER_PERIOD = "min_nominal_interest_rate_per_period";

        public static final String MAX_NOMINAL_INTEREST_RATE_PER_PERIOD = "max_nominal_interest_rate_per_period";

        public static final String INTEREST_PERIOD_FREQUENCY_ENUM = "interest_period_frequency_enum";

        public static final String ANNUAL_NOMINAL_INTEREST_RATE = "annual_nominal_interest_rate";

        public static final String INTEREST_METHOD_ENUM = "interest_method_enum";

        public static final String INTEREST_CALCULATED_IN_PERIOD_ENUM = "interest_calculated_in_period_enum";

        public static final String ALLOW_PARTIAL_PERIOD_INTEREST_CALCUALTION = "allow_partial_period_interest_calcualtion";

        public static final String REPAY_EVERY = "repay_every";

        public static final String REPAYMENT_PERIOD_FREQUENCY_ENUM = "repayment_period_frequency_enum";

        public static final String NUMBER_OF_REPAYMENTS = "number_of_repayments";

        public static final String MIN_NUMBER_OF_REPAYMENTS = "min_number_of_repayments";

        public static final String MAX_NUMBER_OF_REPAYMENTS = "max_number_of_repayments";

        public static final String GRACE_ON_PRINCIPAL_PERIODS = "grace_on_principal_periods";

        public static final String RECURRING_MORATORIUM_PRINCIPAL_PERIODS = "recurring_moratorium_principal_periods";

        public static final String GRACE_ON_INTEREST_PERIODS = "grace_on_interest_periods";

        public static final String GRACE_INTEREST_FREE_PERIODS = "grace_interest_free_periods";

        public static final String AMORTIZATION_METHOD_ENUM = "amortization_method_enum";

        public static final String ACCOUNTING_TYPE = "accounting_type";

        public static final String LOAN_TRANSACTION_STRATEGY_ID = "loan_transaction_strategy_id";

        public static final String EXTERNAL_ID = "external_id";

        public static final String INCLUDE_IN_BORROWER_CYCLE = "include_in_borrower_cycle";

        public static final String USE_BORROWER_CYCLE = "use_borrower_cycle";

        public static final String START_DATE = "start_date";

        public static final String CLOSE_DATE = "close_date";

        public static final String ALLOW_MULTIPLE_DISBURSALS = "allow_multiple_disbursals";

        public static final String MAX_DISBURSALS = "max_disbursals";

        public static final String MAX_OUTSTANDING_LOAN_BALANCE = "max_outstanding_loan_balance";

        public static final String GRACE_ON_ARREARS_AGEING = "grace_on_arrears_ageing";

        public static final String OVERDUE_DAYS_FOR_NPA = "overdue_days_for_npa";

        public static final String DAYS_IN_MONTH_ENUM = "days_in_month_enum";

        public static final String DAYS_IN_YEAR_ENUM = "days_in_year_enum";

        public static final String INTEREST_RECALCULATION_ENABLED = "interest_recalculation_enabled";

        public static final String MIN_DAYS_BETWEEN_DISBURSAL_AND_FIRST_REPAYMENT = "min_days_between_disbursal_and_first_repayment";

        public static final String HOLD_GUARANTEE_FUNDS = "hold_guarantee_funds";

        public static final String PRINCIPAL_THRESHOLD_FOR_LAST_INSTALLMENT = "principal_threshold_for_last_installment";

        public static final String ACCOUNT_MOVES_OUT_OF_NPA_ONLY_ON_ARREARS_COMPLETION = "account_moves_out_of_npa_only_on_arrears_completion";

        public static final String CAN_DEFINE_FIXED_EMI_AMOUNT = "can_define_fixed_emi_amount";

        public static final String INSTALMENT_AMOUNT_IN_MULTIPLES_OF = "instalment_amount_in_multiples_of";

        public static final String CAN_USE_FOR_TOPUP = "can_use_for_topup";

        public static final String SYNC_EXPECTED_WITH_DISBURSEMENT_DATE = "sync_expected_with_disbursement_date";

        public static final String IS_EQUAL_AMORTIZATION = "is_equal_amortization";

    }

}
