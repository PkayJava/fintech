package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MProductLoan extends AbstractTable {

    public final Column ID;

    public final Column SHORT_NAME;

    public final Column CURRENCY_CODE;

    public final Column CURRENCY_DIGIT;

    public final Column CURRENCY_MULTIPLE_OF;

    public final Column PRINCIPAL_AMOUNT;

    public final Column MIN_PRINCIPAL_AMOUNT;

    public final Column MAX_PRINCIPAL_AMOUNT;

    public final Column ARREARS_TOLERANCE_AMOUNT;

    public final Column NAME;

    public final Column DESCRIPTION;

    public final Column FUND_ID;

    public final Column LINKED_TO_FLOATING_INTEREST_RATE;

    public final Column ALLOW_VARIABE_INSTALLMENT;

    public final Column NOMINAL_INTEREST_RATE_PER_PERIOD;

    public final Column MIN_NOMINAL_INTEREST_RATE_PER_PERIOD;

    public final Column MAX_NOMINAL_INTEREST_RATE_PER_PERIOD;

    public final Column INTEREST_PERIOD_FREQUENCY_ENUM;

    public final Column ANNUAL_NOMINAL_INTEREST_RATE;

    public final Column INTEREST_METHOD_ENUM;

    public final Column INTEREST_CALCULATED_IN_PERIOD_ENUM;

    public final Column ALLOW_PARTIAL_PERIOD_INTEREST_CALCUALTION;

    public final Column REPAY_EVERY;

    public final Column REPAYMENT_PERIOD_FREQUENCY_ENUM;

    public final Column NUMBER_OF_REPAYMENT;

    public final Column MIN_NUMBER_OF_REPAYMENT;

    public final Column MAX_NUMBER_OF_REPAYMENT;

    public final Column GRACE_ON_PRINCIPAL_PERIOD;

    public final Column RECURRING_MORATORIUM_PRINCIPAL_PERIOD;

    public final Column GRACE_ON_INTEREST_PERIOD;

    public final Column GRACE_INTEREST_FREE_PERIOD;

    public final Column AMORTIZATION_METHOD_ENUM;

    public final Column ACCOUNTING_TYPE;

    public final Column LOAN_TRANSACTION_STRATEGY_ID;

    public final Column EXTERNAL_ID;

    public final Column INCLUDE_IN_BORROWER_CYCLE;

    public final Column USE_BORROWER_CYCLE;

    public final Column START_DATE;

    public final Column CLOSE_DATE;

    public final Column ALLOW_MULTIPLE_DISBURSAL;

    public final Column MAX_DISBURSAL;

    public final Column MAX_OUTSTANDING_LOAN_BALANCE;

    public final Column GRACE_ON_ARREARS_AGEING;

    public final Column OVERDUE_DAY_FOR_NPA;

    public final Column DAY_IN_MONTH_ENUM;

    public final Column DAY_IN_YEAR_ENUM;

    public final Column INTEREST_RECALCULATION_ENABLED;

    public final Column MIN_DAY_BETWEEN_DISBURSAL_AND_FIRST_REPAYMENT;

    public final Column HOLD_GUARANTEE_FUND;

    public final Column PRINCIPAL_THRESHOLD_FOR_LAST_INSTALLMENT;

    public final Column ACCOUNT_MOVES_OUT_OF_NPA_ONLY_ON_ARREARS_COMPLETION;

    public final Column CAN_DEFINE_FIXED_EMI_AMOUNT;

    public final Column INSTALMENT_AMOUNT_IN_MULTIPLE_OF;

    public final Column CAN_USE_FOR_TOPUP;

    public final Column SYNC_EXPECTED_WITH_DISBURSEMENT_DATE;

    public final Column EQUAL_AMORTIZATION;

    public static MProductLoan staticInitialize(DataContext dataContext) {
        return new MProductLoan(dataContext);
    }

    private MProductLoan(DataContext dataContext) {
        super(dataContext, "m_product_loan");
        this.ID = getInternalColumnByName("id");
        this.SHORT_NAME = getInternalColumnByName("short_name");
        this.CURRENCY_CODE = getInternalColumnByName("currency_code");
        this.CURRENCY_DIGIT = getInternalColumnByName("currency_digits");
        this.CURRENCY_MULTIPLE_OF = getInternalColumnByName("currency_multiplesof");
        this.PRINCIPAL_AMOUNT = getInternalColumnByName("principal_amount");
        this.MIN_PRINCIPAL_AMOUNT = getInternalColumnByName("min_principal_amount");
        this.MAX_PRINCIPAL_AMOUNT = getInternalColumnByName("max_principal_amount");
        this.ARREARS_TOLERANCE_AMOUNT = getInternalColumnByName("arrearstolerance_amount");
        this.NAME = getInternalColumnByName("name");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.FUND_ID = getInternalColumnByName("fund_id");
        this.LINKED_TO_FLOATING_INTEREST_RATE = getInternalColumnByName("is_linked_to_floating_interest_rates");
        this.ALLOW_VARIABE_INSTALLMENT = getInternalColumnByName("allow_variabe_installments");
        this.NOMINAL_INTEREST_RATE_PER_PERIOD = getInternalColumnByName("nominal_interest_rate_per_period");
        this.MIN_NOMINAL_INTEREST_RATE_PER_PERIOD = getInternalColumnByName("min_nominal_interest_rate_per_period");
        this.MAX_NOMINAL_INTEREST_RATE_PER_PERIOD = getInternalColumnByName("max_nominal_interest_rate_per_period");
        this.INTEREST_PERIOD_FREQUENCY_ENUM = getInternalColumnByName("interest_period_frequency_enum");
        this.ANNUAL_NOMINAL_INTEREST_RATE = getInternalColumnByName("annual_nominal_interest_rate");
        this.INTEREST_METHOD_ENUM = getInternalColumnByName("interest_method_enum");
        this.INTEREST_CALCULATED_IN_PERIOD_ENUM = getInternalColumnByName("interest_calculated_in_period_enum");
        this.ALLOW_PARTIAL_PERIOD_INTEREST_CALCUALTION = getInternalColumnByName("allow_partial_period_interest_calcualtion");
        this.REPAY_EVERY = getInternalColumnByName("repay_every");
        this.REPAYMENT_PERIOD_FREQUENCY_ENUM = getInternalColumnByName("repayment_period_frequency_enum");
        this.NUMBER_OF_REPAYMENT = getInternalColumnByName("number_of_repayments");
        this.MIN_NUMBER_OF_REPAYMENT = getInternalColumnByName("min_number_of_repayments");
        this.MAX_NUMBER_OF_REPAYMENT = getInternalColumnByName("max_number_of_repayments");
        this.GRACE_ON_PRINCIPAL_PERIOD = getInternalColumnByName("grace_on_principal_periods");
        this.RECURRING_MORATORIUM_PRINCIPAL_PERIOD = getInternalColumnByName("recurring_moratorium_principal_periods");
        this.GRACE_ON_INTEREST_PERIOD = getInternalColumnByName("grace_on_interest_periods");
        this.GRACE_INTEREST_FREE_PERIOD = getInternalColumnByName("grace_interest_free_periods");
        this.AMORTIZATION_METHOD_ENUM = getInternalColumnByName("amortization_method_enum");
        this.ACCOUNTING_TYPE = getInternalColumnByName("accounting_type");
        this.LOAN_TRANSACTION_STRATEGY_ID = getInternalColumnByName("loan_transaction_strategy_id");
        this.EXTERNAL_ID = getInternalColumnByName("external_id");
        this.INCLUDE_IN_BORROWER_CYCLE = getInternalColumnByName("include_in_borrower_cycle");
        this.USE_BORROWER_CYCLE = getInternalColumnByName("use_borrower_cycle");
        this.START_DATE = getInternalColumnByName("start_date");
        this.CLOSE_DATE = getInternalColumnByName("close_date");
        this.ALLOW_MULTIPLE_DISBURSAL = getInternalColumnByName("allow_multiple_disbursals");
        this.MAX_DISBURSAL = getInternalColumnByName("max_disbursals");
        this.MAX_OUTSTANDING_LOAN_BALANCE = getInternalColumnByName("max_outstanding_loan_balance");
        this.GRACE_ON_ARREARS_AGEING = getInternalColumnByName("grace_on_arrears_ageing");
        this.OVERDUE_DAY_FOR_NPA = getInternalColumnByName("overdue_days_for_npa");
        this.DAY_IN_MONTH_ENUM = getInternalColumnByName("days_in_month_enum");
        this.DAY_IN_YEAR_ENUM = getInternalColumnByName("days_in_year_enum");
        this.INTEREST_RECALCULATION_ENABLED = getInternalColumnByName("interest_recalculation_enabled");
        this.MIN_DAY_BETWEEN_DISBURSAL_AND_FIRST_REPAYMENT = getInternalColumnByName("min_days_between_disbursal_and_first_repayment");
        this.HOLD_GUARANTEE_FUND = getInternalColumnByName("hold_guarantee_funds");
        this.PRINCIPAL_THRESHOLD_FOR_LAST_INSTALLMENT = getInternalColumnByName("principal_threshold_for_last_installment");
        this.ACCOUNT_MOVES_OUT_OF_NPA_ONLY_ON_ARREARS_COMPLETION = getInternalColumnByName("account_moves_out_of_npa_only_on_arrears_completion");
        this.CAN_DEFINE_FIXED_EMI_AMOUNT = getInternalColumnByName("can_define_fixed_emi_amount");
        this.INSTALMENT_AMOUNT_IN_MULTIPLE_OF = getInternalColumnByName("instalment_amount_in_multiples_of");
        this.CAN_USE_FOR_TOPUP = getInternalColumnByName("can_use_for_topup");
        this.SYNC_EXPECTED_WITH_DISBURSEMENT_DATE = getInternalColumnByName("sync_expected_with_disbursement_date");
        this.EQUAL_AMORTIZATION = getInternalColumnByName("is_equal_amortization");
    }

}
