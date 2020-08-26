package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.SHORT_NAME = getInternalColumnByName("short_name");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.DEPOSIT_TYPE_ENUM = getInternalColumnByName("deposit_type_enum");
        this.CURRENCY_CODE = getInternalColumnByName("currency_code");
        this.CURRENCY_DIGIT = getInternalColumnByName("currency_digits");
        this.CURRENCY_MULTIPLE_OF = getInternalColumnByName("currency_multiplesof");
        this.NOMINAL_ANNUAL_INTEREST_RATE = getInternalColumnByName("nominal_annual_interest_rate");
        this.INTEREST_COMPOUNDING_PERIOD_ENUM = getInternalColumnByName("interest_compounding_period_enum");
        this.INTEREST_POSTING_PERIOD_ENUM = getInternalColumnByName("interest_posting_period_enum");
        this.INTEREST_CALCULATION_TYPE_ENUM = getInternalColumnByName("interest_calculation_type_enum");
        this.INTEREST_CALCULATION_DAY_IN_YEAR_TYPE_ENUM = getInternalColumnByName("interest_calculation_days_in_year_type_enum");
        this.MIN_REQUIRED_OPENING_BALANCE = getInternalColumnByName("min_required_opening_balance");
        this.LOCKIN_PERIOD_FREQUENCY = getInternalColumnByName("lockin_period_frequency");
        this.LOCKIN_PERIOD_FREQUENCY_ENUM = getInternalColumnByName("lockin_period_frequency_enum");
        this.ACCOUNTING_TYPE = getInternalColumnByName("accounting_type");
        this.WITHDRAWAL_FEE_AMOUNT = getInternalColumnByName("withdrawal_fee_amount");
        this.WITHDRAWAL_FEE_TYPE_ENUM = getInternalColumnByName("withdrawal_fee_type_enum");
        this.WITHDRAWAL_FEE_FOR_TRANSFER = getInternalColumnByName("withdrawal_fee_for_transfer");
        this.ALLOW_OVERDRAFT = getInternalColumnByName("allow_overdraft");
        this.OVERDRAFT_LIMIT = getInternalColumnByName("overdraft_limit");
        this.NOMINAL_ANNUAL_INTEREST_RATE_OVERDRAFT = getInternalColumnByName("nominal_annual_interest_rate_overdraft");
        this.MIN_OVERDRAFT_FOR_INTEREST_CALCULATION = getInternalColumnByName("min_overdraft_for_interest_calculation");
        this.MIN_REQUIRED_BALANCE = getInternalColumnByName("min_required_balance");
        this.ENFORCE_MIN_REQUIRED_BALANCE = getInternalColumnByName("enforce_min_required_balance");
        this.MIN_BALANCE_FOR_INTEREST_CALCULATION = getInternalColumnByName("min_balance_for_interest_calculation");
        this.WITHHOLD_TAX = getInternalColumnByName("withhold_tax");
        this.TAX_GROUP_ID = getInternalColumnByName("tax_group_id");
        this.DORMANCY_TRACKING_ACTIVE = getInternalColumnByName("is_dormancy_tracking_active");
        this.DAY_TO_INACTIVE = getInternalColumnByName("days_to_inactive");
        this.DAY_TO_DORMANCY = getInternalColumnByName("days_to_dormancy");
        this.DAY_TO_ESCHEAT = getInternalColumnByName("days_to_escheat");
    }

}
