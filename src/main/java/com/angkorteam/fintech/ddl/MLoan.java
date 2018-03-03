package com.angkorteam.fintech.ddl;

public interface MLoan {

    public static final String NAME = "m_loan";

    public interface Field {

        public static final String ID = "id";

        public static final String ACCOUNT_NO = "account_no";

        public static final String EXTERNAL_ID = "external_id";

        public static final String CLIENT_ID = "client_id";

        public static final String GROUP_ID = "group_id";

        public static final String PRODUCT_ID = "product_id";

        public static final String FUND_ID = "fund_id";

        public static final String LOAN_OFFICER_ID = "loan_officer_id";

        public static final String LOAN_PURPOSE_CV_ID = "loanpurpose_cv_id";

        public static final String LOAN_STATUS_ID = "loan_status_id";

        public static final String LOAN_TYPE_ENUM = "loan_type_enum";

        public static final String CURRENCY_CODE = "currency_code";

        public static final String CURRENCY_DIGITS = "currency_digits";

        public static final String CURRENCY_MULTIPLES_OF = "currency_multiplesof";

        public static final String PRINCIPAL_AMOUNT_PROPOSED = "principal_amount_proposed";

        public static final String PRINCIPAL_AMOUNT = "principal_amount";

        public static final String APPROVED_PRINCIPAL = "approved_principal";

        public static final String ARREARS_TOLERANCE_AMOUNT = "arrearstolerance_amount";

        public static final String IS_FLOATING_INTEREST_RATE = "is_floating_interest_rate";

        public static final String INTEREST_RATE_DIFFERENTIAL = "interest_rate_differential";

        public static final String NOMINAL_INTEREST_RATE_PER_PERIOD = "nominal_interest_rate_per_period";

        public static final String INTEREST_PERIOD_FREQUENCY_ENUM = "interest_period_frequency_enum";

        public static final String ANNUAL_NOMINAL_INTEREST_RATE = "annual_nominal_interest_rate";

        public static final String INTEREST_METHOD_ENUM = "interest_method_enum";

        public static final String INTEREST_CALCULATED_IN_PERIOD_ENUM = "interest_calculated_in_period_enum";

        public static final String ALLOW_PARTIAL_PERIOD_INTEREST_CALCUALTION = "allow_partial_period_interest_calcualtion";

        public static final String TERM_FREQUENCY = "term_frequency";

        public static final String TERM_PERIOD_FREQUENCY_ENUM = "term_period_frequency_enum";

        public static final String REPAY_EVERY = "repay_every";

        public static final String REPAYMENT_PERIOD_FREQUENCY_ENUM = "repayment_period_frequency_enum";

        public static final String NUMBER_OF_REPAYMENTS = "number_of_repayments";

        public static final String GRACE_ON_PRINCIPAL_PERIODS = "grace_on_principal_periods";

        public static final String RECURRING_MORATORIUM_PRINCIPAL_PERIODS = "recurring_moratorium_principal_periods";

        public static final String GRACE_ON_INTEREST_PERIODS = "grace_on_interest_periods";

        public static final String GRACE_INTEREST_FREE_PERIODS = "grace_interest_free_periods";

        public static final String AMORTIZATION_METHOD_ENUM = "amortization_method_enum";

        public static final String SUBMITTED_ON_DATE = "submittedon_date";

        public static final String SUBMITTED_ON_USER_ID = "submittedon_userid";

        public static final String APPROVED_ON_DATE = "approvedon_date";

        public static final String APPROVED_ON_USER_ID = "approvedon_userid";

        public static final String EXPECTED_DISBURSED_ON_DATE = "expected_disbursedon_date";

        public static final String EXPECTED_FIRST_REPAYMENTON_DATE = "expected_firstrepaymenton_date";

        public static final String INTEREST_CALCULATED_FROM_DATE = "interest_calculated_from_date";

        public static final String DISBURSED_ON_DATE = "disbursedon_date";

        public static final String DISBURSED_ON_USER_ID = "disbursedon_userid";

        public static final String EXPECTED_MATURED_ON_DATE = "expected_maturedon_date";

        public static final String MATURED_ON_DATE = "maturedon_date";

        public static final String CLOSED_ON_DATE = "closedon_date";

        public static final String CLOSED_ON_USER_ID = "closedon_userid";

        public static final String TOTAL_CHARGES_DUE_AT_DISBURSEMENT_DERIVED = "total_charges_due_at_disbursement_derived";

        public static final String PRINCIPAL_DISBURSED_DERIVED = "principal_disbursed_derived";

        public static final String PRINCIPAL_REPAID_DERIVED = "principal_repaid_derived";

        public static final String PRINCIPAL_WRITTEN_OFF_DERIVED = "principal_writtenoff_derived";

        public static final String PRINCIPAL_OUTSTANDING_DERIVED = "principal_outstanding_derived";

        public static final String INTEREST_CHARGED_DERIVED = "interest_charged_derived";

        public static final String INTEREST_REPAID_DERIVED = "interest_repaid_derived";

        public static final String INTEREST_WAIVED_DERIVED = "interest_waived_derived";

        public static final String INTEREST_WRITTEN_OFF_DERIVED = "interest_writtenoff_derived";

        public static final String INTEREST_OUTSTANDING_DERIVED = "interest_outstanding_derived";

        public static final String FEE_CHARGES_CHARGED_DERIVED = "fee_charges_charged_derived";

        public static final String FEE_CHARGES_REPAID_DERIVED = "fee_charges_repaid_derived";

        public static final String FEE_CHARGES_WAIVED_DERIVED = "fee_charges_waived_derived";

        public static final String FEE_CHARGES_WRITTEN_OFF_DERIVED = "fee_charges_writtenoff_derived";

        public static final String FEE_CHARGES_OUTSTANDING_DERIVED = "fee_charges_outstanding_derived";

        public static final String PENALTY_CHARGES_CHARGED_DERIVED = "penalty_charges_charged_derived";

        public static final String PENALTY_CHARGES_REPAID_DERIVED = "penalty_charges_repaid_derived";

        public static final String PENALTY_CHARGES_WAIVED_DERIVED = "penalty_charges_waived_derived";

        public static final String PENALTY_CHARGES_WRITTEN_OFF_DERIVED = "penalty_charges_writtenoff_derived";

        public static final String PENALTY_CHARGES_OUTSTANDING_DERIVED = "penalty_charges_outstanding_derived";

        public static final String TOTAL_EXPECTED_REPAYMENT_DERIVED = "total_expected_repayment_derived";

        public static final String TOTAL_REPAYMENT_DERIVED = "total_repayment_derived";

        public static final String TOTAL_EXPECTED_COST_OF_LOAN_DERIVED = "total_expected_costofloan_derived";

        public static final String TOTAL_COST_OF_LOAN_DERIVED = "total_costofloan_derived";

        public static final String TOTAL_WAIVED_DERIVED = "total_waived_derived";

        public static final String TOTAL_WRITTEN_OFF_DERIVED = "total_writtenoff_derived";

        public static final String TOTAL_OUTSTANDING_DERIVED = "total_outstanding_derived";

        public static final String TOTAL_OVERPAID_DERIVED = "total_overpaid_derived";

        public static final String REJECTED_ON_DATE = "rejectedon_date";

        public static final String REJECTED_ON_USER_ID = "rejectedon_userid";

        public static final String RESCHEDULED_ON_DATE = "rescheduledon_date";

        public static final String RESCHEDULED_ON_USER_ID = "rescheduledon_userid";

        public static final String WITHDRAWN_ON_DATE = "withdrawnon_date";

        public static final String WITHDRAWN_ON_USER_ID = "withdrawnon_userid";

        public static final String WRITTEN_OFF_ON_DATE = "writtenoffon_date";

        public static final String LOAN_TRANSACTION_STRATEGY_ID = "loan_transaction_strategy_id";

        public static final String SYNC_DISBURSEMENT_WITH_MEETING = "sync_disbursement_with_meeting";

        public static final String LOAN_COUNTER = "loan_counter";

        public static final String LOAN_PRODUCT_COUNTER = "loan_product_counter";

        public static final String FIXED_EMI_AMOUNT = "fixed_emi_amount";

        public static final String MAX_OUTSTANDING_LOAN_BALANCE = "max_outstanding_loan_balance";

        public static final String GRACE_ON_ARREARS_AGEING = "grace_on_arrears_ageing";

        public static final String IS_NPA = "is_npa";

        public static final String TOTAL_RECOVERED_DERIVED = "total_recovered_derived";

        public static final String ACCRUED_TILL = "accrued_till";

        public static final String INTEREST_RECALCUALATED_ON = "interest_recalcualated_on";

        public static final String DAYS_IN_MONTH_ENUM = "days_in_month_enum";

        public static final String DAYS_IN_YEAR_ENUM = "days_in_year_enum";

        public static final String INTEREST_RECALCULATION_ENABLED = "interest_recalculation_enabled";

        public static final String GUARANTEE_AMOUNT_DERIVED = "guarantee_amount_derived";

        public static final String CREATE_STANDING_INSTRUCTION_AT_DISBURSEMENT = "create_standing_instruction_at_disbursement";

        public static final String VERSION = "version";

        public static final String WRITE_OFF_REASON_CV_ID = "writeoff_reason_cv_id";

        public static final String LOAN_SUB_STATUS_ID = "loan_sub_status_id";

        public static final String IS_TOPUP = "is_topup";

        public static final String IS_EQUAL_AMORTIZATION = "is_equal_amortization";

    }

}
