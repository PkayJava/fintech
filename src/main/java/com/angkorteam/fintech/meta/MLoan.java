package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoan extends AbstractTable {

    public final Column ID;

    public final Column ACCOUNT_NO;

    public final Column EXTERNAL_ID;

    public final Column CLIENT_ID;

    public final Column GROUP_ID;

    public final Column GLIM_ID;

    public final Column PRODUCT_ID;

    public final Column FUND_ID;

    public final Column LOAN_OFFICER_ID;

    public final Column LOAN_PURPOSE_CV_ID;

    public final Column LOAN_STATUS_ID;

    public final Column LOAN_TYPE_ENUM;

    public final Column CURRENCY_CODE;

    public final Column CURRENCY_DIGIT;

    public final Column CURRENCY_MULTIPLE_OF;

    public final Column PRINCIPAL_AMOUNT_PROPOSED;

    public final Column PRINCIPAL_AMOUNT;

    public final Column APPROVED_PRINCIPAL;

    public final Column ARREARSTOLERANCE_AMOUNT;

    public final Column FLOATING_INTEREST_RATE;

    public final Column INTEREST_RATE_DIFFERENTIAL;

    public final Column NOMINAL_INTEREST_RATE_PER_PERIOD;

    public final Column INTEREST_PERIOD_FREQUENCY_ENUM;

    public final Column ANNUAL_NOMINAL_INTEREST_RATE;

    public final Column INTEREST_METHOD_ENUM;

    public final Column INTEREST_CALCULATED_IN_PERIOD_ENUM;

    public final Column ALLOW_PARTIAL_PERIOD_INTEREST_CALCUALTION;

    public final Column TERM_FREQUENCY;

    public final Column TERM_PERIOD_FREQUENCY_ENUM;

    public final Column REPAY_EVERY;

    public final Column REPAYMENT_PERIOD_FREQUENCY_ENUM;

    public final Column NUMBER_OF_REPAYMENT;

    public final Column GRACE_ON_PRINCIPAL_PERIOD;

    public final Column RECURRING_MORATORIUM_PRINCIPAL_PERIOD;

    public final Column GRACE_ON_INTEREST_PERIOD;

    public final Column GRACE_INTEREST_FREE_PERIOD;

    public final Column AMORTIZATION_METHOD_ENUM;

    public final Column SUBMITTED_ON_DATE;

    public final Column SUBMITTED_ON_USER_ID;

    public final Column APPROVED_ON_DATE;

    public final Column APPROVED_ON_USER_ID;

    public final Column EXPECTED_DISBURSED_ON_DATE;

    public final Column EXPECTED_FIRST_REPAYMENT_ON_DATE;

    public final Column INTEREST_CALCULATED_FROM_DATE;

    public final Column DISBURSED_ON_USER_ID;

    public final Column EXPECTED_MATURED_ON_DATE;

    public final Column MATURED_ON_DATE;

    public final Column CLOSED_ON_DATE;

    public final Column CLOSED_ON_USER_ID;

    public final Column TOTAL_CHARGE_DUE_AT_DISBURSEMENT_DERIVED;

    public final Column PRINCIPAL_DISBURSED_DERIVED;

    public final Column PRINCIPAL_REPAID_DERIVED;

    public final Column PRINCIPAL_WRITTEN_OFF_DERIVED;

    public final Column PRINCIPAL_OUTSTANDING_DERIVED;

    public final Column INTEREST_CHARGED_DERIVED;

    public final Column INTEREST_REPAID_DERIVED;

    public final Column INTEREST_WAIVED_DERIVED;

    public final Column INTEREST_WRITTEN_OFF_DERIVED;

    public final Column INTEREST_OUTSTANDING_DERIVED;

    public final Column FEE_CHARGE_CHARGED_DERIVED;

    public final Column FEE_CHARGE_REPAID_DERIVED;

    public final Column FEE_CHARGE_WAIVED_DERIVED;

    public final Column FEE_CHARGE_WRITTEN_OFF_DERIVED;

    public final Column FEE_CHARGE_OUTSTANDING_DERIVED;

    public final Column PENALTY_CHARGE_CHARGED_DERIVED;

    public final Column PENALTY_CHARGE_REPAID_DERIVED;

    public final Column PENALTY_CHARGE_WAIVED_DERIVED;

    public final Column PENALTY_CHARGE_WRITTEN_OFF_DERIVED;

    public final Column PENALTY_CHARGE_OUTSTANDING_DERIVED;

    public final Column TOTAL_EXPECTED_REPAYMENT_DERIVED;

    public final Column TOTAL_REPAYMENT_DERIVED;

    public final Column TOTAL_EXPECTED_COST_OF_LOAN_DERIVED;

    public final Column TOTAL_COST_OF_LOAN_DERIVED;

    public final Column TOTAL_WAIVED_DERIVED;

    public final Column TOTAL_WRITTEN_OFF_DERIVED;

    public final Column TOTAL_OUTSTANDING_DERIVED;

    public final Column TOTAL_OVERPAID_DERIVED;

    public final Column REJECTED_ON_DATE;

    public final Column REJECTED_ON_USER_ID;

    public final Column RESCHEDULED_ON_DATE;

    public final Column RESCHEDULED_ON_USER_ID;

    public final Column WITHDRAWN_ON_DATE;

    public final Column WITHDRAWN_ON_USER_ID;

    public final Column WRITTEN_OFF_ON_DATE;

    public final Column LOAN_TRANSACTION_STRATEGY_ID;

    public final Column SYNC_DISBURSEMENT_WITH_MEETING;

    public final Column LOAN_COUNTER;

    public final Column LOAN_PRODUCT_COUNTER;

    public final Column FIXED_EMI_AMOUNT;

    public final Column MAX_OUTSTANDING_LOAN_BALANCE;

    public final Column GRACE_ON_ARREARS_AGEING;

    public final Column NPA;

    public final Column TOTAL_RECOVERED_DERIVED;

    public final Column ACCRUED_TILL;

    public final Column INTEREST_RECALCUALATED_ON;

    public final Column DAY_IN_MONTH_ENUM;

    public final Column DAY_IN_YEAR_ENUM;

    public final Column INTEREST_RECALCULATION_ENABLED;

    public final Column GUARANTEE_AMOUNT_DERIVED;

    public final Column CREATE_STANDING_INSTRUCTION_AT_DISBURSEMENT;

    public final Column VERSION;

    public final Column WRITE_OFF_REASON_CV_ID;

    public final Column LOAN_SUB_STATUS_ID;

    public final Column TOPUP;

    public final Column EQUAL_AMORTIZATION;

    public static MLoan staticInitialize(DataContext dataContext) {
        return new MLoan(dataContext);
    }

    private MLoan(DataContext dataContext) {
        super(dataContext, "m_loan");
        this.ID = this.delegate.getColumnByName("id");
        this.ACCOUNT_NO = this.delegate.getColumnByName("account_no");
        this.EXTERNAL_ID = this.delegate.getColumnByName("external_id");
        this.CLIENT_ID = this.delegate.getColumnByName("client_id");
        this.GROUP_ID = this.delegate.getColumnByName("group_id");
        this.GLIM_ID = this.delegate.getColumnByName("glim_id");
        this.PRODUCT_ID = this.delegate.getColumnByName("product_id");
        this.FUND_ID = this.delegate.getColumnByName("fund_id");
        this.LOAN_OFFICER_ID = this.delegate.getColumnByName("loan_officer_id");
        this.LOAN_PURPOSE_CV_ID = this.delegate.getColumnByName("loanpurpose_cv_id");
        this.LOAN_STATUS_ID = this.delegate.getColumnByName("loan_status_id");
        this.LOAN_TYPE_ENUM = this.delegate.getColumnByName("loan_type_enum");
        this.CURRENCY_CODE = this.delegate.getColumnByName("currency_code");
        this.CURRENCY_DIGIT = this.delegate.getColumnByName("currency_digits");
        this.CURRENCY_MULTIPLE_OF = this.delegate.getColumnByName("currency_multiplesof");
        this.PRINCIPAL_AMOUNT_PROPOSED = this.delegate.getColumnByName("principal_amount_proposed");
        this.PRINCIPAL_AMOUNT = this.delegate.getColumnByName("principal_amount");
        this.APPROVED_PRINCIPAL = this.delegate.getColumnByName("approved_principal");
        this.ARREARSTOLERANCE_AMOUNT = this.delegate.getColumnByName("arrearstolerance_amount");
        this.FLOATING_INTEREST_RATE = this.delegate.getColumnByName("is_floating_interest_rate");
        this.INTEREST_RATE_DIFFERENTIAL = this.delegate.getColumnByName("interest_rate_differential");
        this.NOMINAL_INTEREST_RATE_PER_PERIOD = this.delegate.getColumnByName("nominal_interest_rate_per_period");
        this.INTEREST_PERIOD_FREQUENCY_ENUM = this.delegate.getColumnByName("interest_period_frequency_enum");
        this.ANNUAL_NOMINAL_INTEREST_RATE = this.delegate.getColumnByName("annual_nominal_interest_rate");
        this.INTEREST_METHOD_ENUM = this.delegate.getColumnByName("interest_method_enum");
        this.INTEREST_CALCULATED_IN_PERIOD_ENUM = this.delegate.getColumnByName("interest_calculated_in_period_enum");
        this.ALLOW_PARTIAL_PERIOD_INTEREST_CALCUALTION = this.delegate.getColumnByName("allow_partial_period_interest_calcualtion");
        this.TERM_FREQUENCY = this.delegate.getColumnByName("term_frequency");
        this.TERM_PERIOD_FREQUENCY_ENUM = this.delegate.getColumnByName("term_period_frequency_enum");
        this.REPAY_EVERY = this.delegate.getColumnByName("repay_every");
        this.REPAYMENT_PERIOD_FREQUENCY_ENUM = this.delegate.getColumnByName("repayment_period_frequency_enum");
        this.NUMBER_OF_REPAYMENT = this.delegate.getColumnByName("number_of_repayments");
        this.GRACE_ON_PRINCIPAL_PERIOD = this.delegate.getColumnByName("grace_on_principal_periods");
        this.RECURRING_MORATORIUM_PRINCIPAL_PERIOD = this.delegate.getColumnByName("recurring_moratorium_principal_periods");
        this.GRACE_ON_INTEREST_PERIOD = this.delegate.getColumnByName("grace_on_interest_periods");
        this.GRACE_INTEREST_FREE_PERIOD = this.delegate.getColumnByName("grace_interest_free_periods");
        this.AMORTIZATION_METHOD_ENUM = this.delegate.getColumnByName("amortization_method_enum");
        this.SUBMITTED_ON_DATE = this.delegate.getColumnByName("submittedon_date");
        this.SUBMITTED_ON_USER_ID = this.delegate.getColumnByName("submittedon_userid");
        this.APPROVED_ON_DATE = this.delegate.getColumnByName("approvedon_date");
        this.APPROVED_ON_USER_ID = this.delegate.getColumnByName("approvedon_userid");
        this.EXPECTED_DISBURSED_ON_DATE = this.delegate.getColumnByName("expected_disbursedon_date");
        this.EXPECTED_FIRST_REPAYMENT_ON_DATE = this.delegate.getColumnByName("expected_firstrepaymenton_date");
        this.INTEREST_CALCULATED_FROM_DATE = this.delegate.getColumnByName("interest_calculated_from_date");
        this.DISBURSED_ON_USER_ID = this.delegate.getColumnByName("disbursedon_userid");
        this.EXPECTED_MATURED_ON_DATE = this.delegate.getColumnByName("expected_maturedon_date");
        this.MATURED_ON_DATE = this.delegate.getColumnByName("maturedon_date");
        this.CLOSED_ON_DATE = this.delegate.getColumnByName("closedon_date");
        this.CLOSED_ON_USER_ID = this.delegate.getColumnByName("closedon_userid");
        this.TOTAL_CHARGE_DUE_AT_DISBURSEMENT_DERIVED = this.delegate.getColumnByName("total_charges_due_at_disbursement_derived");
        this.PRINCIPAL_DISBURSED_DERIVED = this.delegate.getColumnByName("principal_disbursed_derived");
        this.PRINCIPAL_REPAID_DERIVED = this.delegate.getColumnByName("principal_repaid_derived");
        this.PRINCIPAL_WRITTEN_OFF_DERIVED = this.delegate.getColumnByName("principal_writtenoff_derived");
        this.PRINCIPAL_OUTSTANDING_DERIVED = this.delegate.getColumnByName("principal_outstanding_derived");
        this.INTEREST_CHARGED_DERIVED = this.delegate.getColumnByName("interest_charged_derived");
        this.INTEREST_REPAID_DERIVED = this.delegate.getColumnByName("interest_repaid_derived");
        this.INTEREST_WAIVED_DERIVED = this.delegate.getColumnByName("interest_waived_derived");
        this.INTEREST_WRITTEN_OFF_DERIVED = this.delegate.getColumnByName("interest_writtenoff_derived");
        this.INTEREST_OUTSTANDING_DERIVED = this.delegate.getColumnByName("interest_outstanding_derived");
        this.FEE_CHARGE_CHARGED_DERIVED = this.delegate.getColumnByName("fee_charges_charged_derived");
        this.FEE_CHARGE_REPAID_DERIVED = this.delegate.getColumnByName("fee_charges_repaid_derived");
        this.FEE_CHARGE_WAIVED_DERIVED = this.delegate.getColumnByName("fee_charges_waived_derived");
        this.FEE_CHARGE_WRITTEN_OFF_DERIVED = this.delegate.getColumnByName("fee_charges_writtenoff_derived");
        this.FEE_CHARGE_OUTSTANDING_DERIVED = this.delegate.getColumnByName("fee_charges_outstanding_derived");
        this.PENALTY_CHARGE_CHARGED_DERIVED = this.delegate.getColumnByName("penalty_charges_charged_derived");
        this.PENALTY_CHARGE_REPAID_DERIVED = this.delegate.getColumnByName("penalty_charges_repaid_derived");
        this.PENALTY_CHARGE_WAIVED_DERIVED = this.delegate.getColumnByName("penalty_charges_waived_derived");
        this.PENALTY_CHARGE_WRITTEN_OFF_DERIVED = this.delegate.getColumnByName("penalty_charges_writtenoff_derived");
        this.PENALTY_CHARGE_OUTSTANDING_DERIVED = this.delegate.getColumnByName("penalty_charges_outstanding_derived");
        this.TOTAL_EXPECTED_REPAYMENT_DERIVED = this.delegate.getColumnByName("total_expected_repayment_derived");
        this.TOTAL_REPAYMENT_DERIVED = this.delegate.getColumnByName("total_repayment_derived");
        this.TOTAL_EXPECTED_COST_OF_LOAN_DERIVED = this.delegate.getColumnByName("total_expected_costofloan_derived");
        this.TOTAL_COST_OF_LOAN_DERIVED = this.delegate.getColumnByName("total_costofloan_derived");
        this.TOTAL_WAIVED_DERIVED = this.delegate.getColumnByName("total_waived_derived");
        this.TOTAL_WRITTEN_OFF_DERIVED = this.delegate.getColumnByName("total_writtenoff_derived");
        this.TOTAL_OUTSTANDING_DERIVED = this.delegate.getColumnByName("total_outstanding_derived");
        this.TOTAL_OVERPAID_DERIVED = this.delegate.getColumnByName("total_overpaid_derived");
        this.REJECTED_ON_DATE = this.delegate.getColumnByName("rejectedon_date");
        this.REJECTED_ON_USER_ID = this.delegate.getColumnByName("rejectedon_userid");
        this.RESCHEDULED_ON_DATE = this.delegate.getColumnByName("rescheduledon_date");
        this.RESCHEDULED_ON_USER_ID = this.delegate.getColumnByName("rescheduledon_userid");
        this.WITHDRAWN_ON_DATE = this.delegate.getColumnByName("withdrawnon_date");
        this.WITHDRAWN_ON_USER_ID = this.delegate.getColumnByName("withdrawnon_userid");
        this.WRITTEN_OFF_ON_DATE = this.delegate.getColumnByName("writtenoffon_date");
        this.LOAN_TRANSACTION_STRATEGY_ID = this.delegate.getColumnByName("loan_transaction_strategy_id");
        this.SYNC_DISBURSEMENT_WITH_MEETING = this.delegate.getColumnByName("sync_disbursement_with_meeting");
        this.LOAN_COUNTER = this.delegate.getColumnByName("loan_counter");
        this.LOAN_PRODUCT_COUNTER = this.delegate.getColumnByName("loan_product_counter");
        this.FIXED_EMI_AMOUNT = this.delegate.getColumnByName("fixed_emi_amount");
        this.MAX_OUTSTANDING_LOAN_BALANCE = this.delegate.getColumnByName("max_outstanding_loan_balance");
        this.GRACE_ON_ARREARS_AGEING = this.delegate.getColumnByName("grace_on_arrears_ageing");
        this.NPA = this.delegate.getColumnByName("is_npa");
        this.TOTAL_RECOVERED_DERIVED = this.delegate.getColumnByName("total_recovered_derived");
        this.ACCRUED_TILL = this.delegate.getColumnByName("accrued_till");
        this.INTEREST_RECALCUALATED_ON = this.delegate.getColumnByName("interest_recalcualated_on");
        this.DAY_IN_MONTH_ENUM = this.delegate.getColumnByName("days_in_month_enum");
        this.DAY_IN_YEAR_ENUM = this.delegate.getColumnByName("days_in_year_enum");
        this.INTEREST_RECALCULATION_ENABLED = this.delegate.getColumnByName("interest_recalculation_enabled");
        this.GUARANTEE_AMOUNT_DERIVED = this.delegate.getColumnByName("guarantee_amount_derived");
        this.CREATE_STANDING_INSTRUCTION_AT_DISBURSEMENT = this.delegate.getColumnByName("create_standing_instruction_at_disbursement");
        this.VERSION = this.delegate.getColumnByName("version");
        this.WRITE_OFF_REASON_CV_ID = this.delegate.getColumnByName("writeoff_reason_cv_id");
        this.LOAN_SUB_STATUS_ID = this.delegate.getColumnByName("loan_sub_status_id");
        this.TOPUP = this.delegate.getColumnByName("is_topup");
        this.EQUAL_AMORTIZATION = this.delegate.getColumnByName("is_equal_amortization");
    }

}