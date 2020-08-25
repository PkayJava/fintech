package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSavingAccount extends AbstractTable {

    public final Column ID;

    public final Column ACCOUNT_NO;

    public final Column EXTERNAL_ID;

    public final Column CLIENT_ID;

    public final Column GROUP_ID;

    public final Column GSIM_ID;

    public final Column PRODUCT_ID;

    public final Column FIELD_OFFICER_ID;

    public final Column STATUS_ENUM;

    public final Column SUB_STATUS_ENUM;

    public final Column ACCOUNT_TYPE_ENUM;

    public final Column DEPOSIT_TYPE_ENUM;

    public final Column SUBMITTED_ON_DATE;

    public final Column SUBMITTED_ON_USER_ID;

    public final Column APPROVED_ON_DATE;

    public final Column APPROVED_ON_USER_ID;

    public final Column REJECTED_ON_DATE;

    public final Column REJECTED_ON_USER_ID;

    public final Column WITHDRAWN_ON_DATE;

    public final Column WITHDRAWN_ON_USER_ID;

    public final Column ACTIVATED_ON_DATE;

    public final Column ACTIVATED_ON_USER_ID;

    public final Column CLOSED_ON_DATE;

    public final Column CLOSED_ON_USER_ID;

    public final Column CURRENCY_CODE;

    public final Column CURRENCY_DIGIT;

    public final Column CURRENCY_MULTIPLE_OF;

    public final Column NOMINAL_ANNUAL_INTEREST_RATE;

    public final Column INTEREST_COMPOUNDING_PERIOD_ENUM;

    public final Column INTEREST_POSTING_PERIOD_ENUM;

    public final Column INTEREST_CALCULATION_TYPE_ENUM;

    public final Column INTEREST_CALCULATION_DAYS_IN_YEAR_TYPE_ENUM;

    public final Column MIN_REQUIRED_OPENING_BALANCE;

    public final Column LOCKIN_PERIOD_FREQUENCY;

    public final Column LOCKIN_PERIOD_FREQUENCY_ENUM;

    public final Column WITHDRAWAL_FEE_FOR_TRANSFER;

    public final Column ALLOW_OVERDRAFT;

    public final Column OVERDRAFT_LIMIT;

    public final Column NOMINAL_ANNUAL_INTEREST_RATE_OVERDRAFT;

    public final Column MIN_OVERDRAFT_FOR_INTEREST_CALCULATION;

    public final Column LOCKEDIN_UNTIL_DATE_DERIVED;

    public final Column TOTAL_DEPOSITS_DERIVED;

    public final Column TOTAL_WITHDRAWAL_DERIVED;

    public final Column TOTAL_WITHDRAWAL_FEE_DERIVED;

    public final Column TOTAL_FEE_CHARGE_DERIVED;

    public final Column TOTAL_PENALTY_CHARGE_DERIVED;

    public final Column TOTAL_ANNUAL_FEE_DERIVED;

    public final Column TOTAL_INTEREST_EARNED_DERIVED;

    public final Column TOTAL_INTEREST_POSTED_DERIVED;

    public final Column TOTAL_OVERDRAFT_INTEREST_DERIVED;

    public final Column TOTAL_WITHHOLD_TAX_DERIVED;

    public final Column ACCOUNT_BALANCE_DERIVED;

    public final Column MIN_REQUIRED_BALANCE;

    public final Column ENFORCE_MIN_REQUIRED_BALANCE;

    public final Column MIN_BALANCE_FOR_INTEREST_CALCULATION;

    public final Column START_INTEREST_CALCULATION_DATE;

    public final Column ON_HOLD_FUND_DERIVED;

    public final Column VERSION;

    public final Column WITHHOLD_TAX;

    public final Column TAX_GROUP_ID;

    public final Column LAST_INTEREST_CALCULATION_DATE;

    public final Column TOTAL_SAVING_AMOUNT_ON_HOLD;

    public static MSavingAccount staticInitialize(DataContext dataContext) {
        return new MSavingAccount(dataContext);
    }

    private MSavingAccount(DataContext dataContext) {
        super(dataContext, "m_savings_account");
        this.ID = getInternalColumnByName("id");
        this.ACCOUNT_NO = getInternalColumnByName("account_no");
        this.EXTERNAL_ID = getInternalColumnByName("external_id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.GROUP_ID = getInternalColumnByName("group_id");
        this.GSIM_ID = getInternalColumnByName("gsim_id");
        this.PRODUCT_ID = getInternalColumnByName("product_id");
        this.FIELD_OFFICER_ID = getInternalColumnByName("field_officer_id");
        this.STATUS_ENUM = getInternalColumnByName("status_enum");
        this.SUB_STATUS_ENUM = getInternalColumnByName("sub_status_enum");
        this.ACCOUNT_TYPE_ENUM = getInternalColumnByName("account_type_enum");
        this.DEPOSIT_TYPE_ENUM = getInternalColumnByName("deposit_type_enum");
        this.SUBMITTED_ON_DATE = getInternalColumnByName("submittedon_date");
        this.SUBMITTED_ON_USER_ID = getInternalColumnByName("submittedon_userid");
        this.APPROVED_ON_DATE = getInternalColumnByName("approvedon_date");
        this.APPROVED_ON_USER_ID = getInternalColumnByName("approvedon_userid");
        this.REJECTED_ON_DATE = getInternalColumnByName("rejectedon_date");
        this.REJECTED_ON_USER_ID = getInternalColumnByName("rejectedon_userid");
        this.WITHDRAWN_ON_DATE = getInternalColumnByName("withdrawnon_date");
        this.WITHDRAWN_ON_USER_ID = getInternalColumnByName("withdrawnon_userid");
        this.ACTIVATED_ON_DATE = getInternalColumnByName("activatedon_date");
        this.ACTIVATED_ON_USER_ID = getInternalColumnByName("activatedon_userid");
        this.CLOSED_ON_DATE = getInternalColumnByName("closedon_date");
        this.CLOSED_ON_USER_ID = getInternalColumnByName("closedon_userid");
        this.CURRENCY_CODE = getInternalColumnByName("currency_code");
        this.CURRENCY_DIGIT = getInternalColumnByName("currency_digits");
        this.CURRENCY_MULTIPLE_OF = getInternalColumnByName("currency_multiplesof");
        this.NOMINAL_ANNUAL_INTEREST_RATE = getInternalColumnByName("nominal_annual_interest_rate");
        this.INTEREST_COMPOUNDING_PERIOD_ENUM = getInternalColumnByName("interest_compounding_period_enum");
        this.INTEREST_POSTING_PERIOD_ENUM = getInternalColumnByName("interest_posting_period_enum");
        this.INTEREST_CALCULATION_TYPE_ENUM = getInternalColumnByName("interest_calculation_type_enum");
        this.INTEREST_CALCULATION_DAYS_IN_YEAR_TYPE_ENUM = getInternalColumnByName("interest_calculation_days_in_year_type_enum");
        this.MIN_REQUIRED_OPENING_BALANCE = getInternalColumnByName("min_required_opening_balance");
        this.LOCKIN_PERIOD_FREQUENCY = getInternalColumnByName("lockin_period_frequency");
        this.LOCKIN_PERIOD_FREQUENCY_ENUM = getInternalColumnByName("lockin_period_frequency_enum");
        this.WITHDRAWAL_FEE_FOR_TRANSFER = getInternalColumnByName("withdrawal_fee_for_transfer");
        this.ALLOW_OVERDRAFT = getInternalColumnByName("allow_overdraft");
        this.OVERDRAFT_LIMIT = getInternalColumnByName("overdraft_limit");
        this.NOMINAL_ANNUAL_INTEREST_RATE_OVERDRAFT = getInternalColumnByName("nominal_annual_interest_rate_overdraft");
        this.MIN_OVERDRAFT_FOR_INTEREST_CALCULATION = getInternalColumnByName("min_overdraft_for_interest_calculation");
        this.LOCKEDIN_UNTIL_DATE_DERIVED = getInternalColumnByName("lockedin_until_date_derived");
        this.TOTAL_DEPOSITS_DERIVED = getInternalColumnByName("total_deposits_derived");
        this.TOTAL_WITHDRAWAL_DERIVED = getInternalColumnByName("total_withdrawals_derived");
        this.TOTAL_WITHDRAWAL_FEE_DERIVED = getInternalColumnByName("total_withdrawal_fees_derived");
        this.TOTAL_FEE_CHARGE_DERIVED = getInternalColumnByName("total_fees_charge_derived");
        this.TOTAL_PENALTY_CHARGE_DERIVED = getInternalColumnByName("total_penalty_charge_derived");
        this.TOTAL_ANNUAL_FEE_DERIVED = getInternalColumnByName("total_annual_fees_derived");
        this.TOTAL_INTEREST_EARNED_DERIVED = getInternalColumnByName("total_interest_earned_derived");
        this.TOTAL_INTEREST_POSTED_DERIVED = getInternalColumnByName("total_interest_posted_derived");
        this.TOTAL_OVERDRAFT_INTEREST_DERIVED = getInternalColumnByName("total_overdraft_interest_derived");
        this.TOTAL_WITHHOLD_TAX_DERIVED = getInternalColumnByName("total_withhold_tax_derived");
        this.ACCOUNT_BALANCE_DERIVED = getInternalColumnByName("account_balance_derived");
        this.MIN_REQUIRED_BALANCE = getInternalColumnByName("min_required_balance");
        this.ENFORCE_MIN_REQUIRED_BALANCE = getInternalColumnByName("enforce_min_required_balance");
        this.MIN_BALANCE_FOR_INTEREST_CALCULATION = getInternalColumnByName("min_balance_for_interest_calculation");
        this.START_INTEREST_CALCULATION_DATE = getInternalColumnByName("start_interest_calculation_date");
        this.ON_HOLD_FUND_DERIVED = getInternalColumnByName("on_hold_funds_derived");
        this.VERSION = getInternalColumnByName("version");
        this.WITHHOLD_TAX = getInternalColumnByName("withhold_tax");
        this.TAX_GROUP_ID = getInternalColumnByName("tax_group_id");
        this.LAST_INTEREST_CALCULATION_DATE = getInternalColumnByName("last_interest_calculation_date");
        this.TOTAL_SAVING_AMOUNT_ON_HOLD = getInternalColumnByName("total_savings_amount_on_hold");
    }

}
