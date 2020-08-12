package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MCharge extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column CURRENCY_CODE;

    public final Column CHARGE_APPLY_TO_ENUM;

    public final Column CHARGE_TIME_ENUM;

    public final Column CHARGE_CALCULATION_ENUM;

    public final Column CHARGE_PAYMENT_MODE_ENUM;

    public final Column AMOUNT;

    public final Column FEE_ON_DAY;

    public final Column FEE_INTERVAL;

    public final Column FEE_ON_MONTH;

    public final Column PENALTY;

    public final Column ACTIVE;

    public final Column DELETED;

    public final Column MIN_CAP;

    public final Column MAX_CAP;

    public final Column FEE_FREQUENCY;

    public final Column INCOME_OR_LIABILITY_ACCOUNT_ID;

    public final Column TAX_GROUP_ID;

    public static MCharge staticInitialize(DataContext dataContext) {
        return new MCharge(dataContext);
    }

    private MCharge(DataContext dataContext) {
        super(dataContext, "m_charge");
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
        this.CURRENCY_CODE = this.delegate.getColumnByName("currency_code");
        this.CHARGE_APPLY_TO_ENUM = this.delegate.getColumnByName("charge_applies_to_enum");
        this.CHARGE_TIME_ENUM = this.delegate.getColumnByName("charge_time_enum");
        this.CHARGE_CALCULATION_ENUM = this.delegate.getColumnByName("charge_calculation_enum");
        this.CHARGE_PAYMENT_MODE_ENUM = this.delegate.getColumnByName("charge_payment_mode_enum");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.FEE_ON_DAY = this.delegate.getColumnByName("fee_on_day");
        this.FEE_INTERVAL = this.delegate.getColumnByName("fee_interval");
        this.FEE_ON_MONTH = this.delegate.getColumnByName("fee_on_month");
        this.PENALTY = this.delegate.getColumnByName("is_penalty");
        this.ACTIVE = this.delegate.getColumnByName("is_active");
        this.DELETED = this.delegate.getColumnByName("is_deleted");
        this.MIN_CAP = this.delegate.getColumnByName("min_cap");
        this.MAX_CAP = this.delegate.getColumnByName("max_cap");
        this.FEE_FREQUENCY = this.delegate.getColumnByName("fee_frequency");
        this.INCOME_OR_LIABILITY_ACCOUNT_ID = this.delegate.getColumnByName("income_or_liability_account_id");
        this.TAX_GROUP_ID = this.delegate.getColumnByName("tax_group_id");
    }

}
