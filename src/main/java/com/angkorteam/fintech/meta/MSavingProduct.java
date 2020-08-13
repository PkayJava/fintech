package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSavingProduct extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column SHORT_NAME;

    public final Column DESCRIPTION;

    public final Column DEPOSIT_TYPE_ENUM;

    public final Column CURRENCY_CODE;

    public final Column CURRENCY_DIGIT;

    public final Column CURRENCY_MULTIPLE_OF;

    public final Column NOMINAL_ANNUAL_INTEREST_RATE;

    public final Column INTEREST_COMPOUNDING_PERIOD_ENUM;

    public final Column INTEREST_POSTING_PERIOD_ENUM;

    public final Column INTEREST_CALCULATION_TYPE_ENUM;

    public final Column INTEREST_CALCULATION_DAY_IN_YEAR_TYPE_ENUM;

    public final Column MIN_REQUIRED_OPENING_BALANCE;

    public final Column LOCKIN_PERIOD_FREQUENCY;

    public final Column LOCKIN_PERIOD_FREQUENCY_ENUM;

    public final Column ACCOUNTING_TYPE;

    public final Column WITHDRAWAL_FEE_AMOUNT;

    public final Column WITHDRAWAL_FEE_TYPE_ENUM;

    public final Column WITHDRAWAL_FEE_FOR_TRANSFER;

    public final Column ALLOW_OVERDRAFT;

    public final Column OVERDRAFT_LIMIT;

    public final Column NOMINAL_ANNUAL_INTEREST_RATE_OVERDRAFT;

    public final Column MIN_OVERDRAFT_FOR_INTEREST_CALCULATION;

    public final Column MIN_REQUIRED_BALANCE;

    public final Column ENFORCE_MIN_REQUIRED_BALANCE;

    public final Column MIN_BALANCE_FOR_INTEREST_CALCULATION;

    public final Column WITHHOLD_TAX;

    public final Column TAX_GROUP_ID;

    public final Column DORMANCY_TRACKING_ACTIVE;

    public final Column DAY_TO_INACTIVE;

    public final Column DAY_TO_DORMANCY;

    public final Column DAY_TO_ESCHEAT;

    public static MSavingProduct staticInitialize(DataContext dataContext) {
        return new MSavingProduct(dataContext);
    }

    private MSavingProduct(DataContext dataContext) {
        super(dataContext, "m_savings_product");
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
        this.SHORT_NAME = this.delegate.getColumnByName("short_name");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.DEPOSIT_TYPE_ENUM = this.delegate.getColumnByName("deposit_type_enum");
        this.CURRENCY_CODE = this.delegate.getColumnByName("currency_code");
        this.CURRENCY_DIGIT = this.delegate.getColumnByName("currency_digits");
        this.CURRENCY_MULTIPLE_OF = this.delegate.getColumnByName("currency_multiplesof");
        this.NOMINAL_ANNUAL_INTEREST_RATE = this.delegate.getColumnByName("nominal_annual_interest_rate");
        this.INTEREST_COMPOUNDING_PERIOD_ENUM = this.delegate.getColumnByName("interest_compounding_period_enum");
        this.INTEREST_POSTING_PERIOD_ENUM = this.delegate.getColumnByName("interest_posting_period_enum");
        this.INTEREST_CALCULATION_TYPE_ENUM = this.delegate.getColumnByName("interest_calculation_type_enum");
        this.INTEREST_CALCULATION_DAY_IN_YEAR_TYPE_ENUM = this.delegate.getColumnByName("interest_calculation_days_in_year_type_enum");
        this.MIN_REQUIRED_OPENING_BALANCE = this.delegate.getColumnByName("min_required_opening_balance");
        this.LOCKIN_PERIOD_FREQUENCY = this.delegate.getColumnByName("lockin_period_frequency");
        this.LOCKIN_PERIOD_FREQUENCY_ENUM = this.delegate.getColumnByName("lockin_period_frequency_enum");
        this.ACCOUNTING_TYPE = this.delegate.getColumnByName("accounting_type");
        this.WITHDRAWAL_FEE_AMOUNT = this.delegate.getColumnByName("withdrawal_fee_amount");
        this.WITHDRAWAL_FEE_TYPE_ENUM = this.delegate.getColumnByName("withdrawal_fee_type_enum");
        this.WITHDRAWAL_FEE_FOR_TRANSFER = this.delegate.getColumnByName("withdrawal_fee_for_transfer");
        this.ALLOW_OVERDRAFT = this.delegate.getColumnByName("allow_overdraft");
        this.OVERDRAFT_LIMIT = this.delegate.getColumnByName("overdraft_limit");
        this.NOMINAL_ANNUAL_INTEREST_RATE_OVERDRAFT = this.delegate.getColumnByName("nominal_annual_interest_rate_overdraft");
        this.MIN_OVERDRAFT_FOR_INTEREST_CALCULATION = this.delegate.getColumnByName("min_overdraft_for_interest_calculation");
        this.MIN_REQUIRED_BALANCE = this.delegate.getColumnByName("min_required_balance");
        this.ENFORCE_MIN_REQUIRED_BALANCE = this.delegate.getColumnByName("enforce_min_required_balance");
        this.MIN_BALANCE_FOR_INTEREST_CALCULATION = this.delegate.getColumnByName("min_balance_for_interest_calculation");
        this.WITHHOLD_TAX = this.delegate.getColumnByName("withhold_tax");
        this.TAX_GROUP_ID = this.delegate.getColumnByName("tax_group_id");
        this.DORMANCY_TRACKING_ACTIVE = this.delegate.getColumnByName("is_dormancy_tracking_active");
        this.DAY_TO_INACTIVE = this.delegate.getColumnByName("days_to_inactive");
        this.DAY_TO_DORMANCY = this.delegate.getColumnByName("days_to_dormancy");
        this.DAY_TO_ESCHEAT = this.delegate.getColumnByName("days_to_escheat");
    }

}
