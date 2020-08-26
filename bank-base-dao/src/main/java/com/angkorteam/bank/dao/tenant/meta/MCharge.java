package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.CURRENCY_CODE = getInternalColumnByName("currency_code");
        this.CHARGE_APPLY_TO_ENUM = getInternalColumnByName("charge_applies_to_enum");
        this.CHARGE_TIME_ENUM = getInternalColumnByName("charge_time_enum");
        this.CHARGE_CALCULATION_ENUM = getInternalColumnByName("charge_calculation_enum");
        this.CHARGE_PAYMENT_MODE_ENUM = getInternalColumnByName("charge_payment_mode_enum");
        this.AMOUNT = getInternalColumnByName("amount");
        this.FEE_ON_DAY = getInternalColumnByName("fee_on_day");
        this.FEE_INTERVAL = getInternalColumnByName("fee_interval");
        this.FEE_ON_MONTH = getInternalColumnByName("fee_on_month");
        this.PENALTY = getInternalColumnByName("is_penalty");
        this.ACTIVE = getInternalColumnByName("is_active");
        this.DELETED = getInternalColumnByName("is_deleted");
        this.MIN_CAP = getInternalColumnByName("min_cap");
        this.MAX_CAP = getInternalColumnByName("max_cap");
        this.FEE_FREQUENCY = getInternalColumnByName("fee_frequency");
        this.INCOME_OR_LIABILITY_ACCOUNT_ID = getInternalColumnByName("income_or_liability_account_id");
        this.TAX_GROUP_ID = getInternalColumnByName("tax_group_id");
    }

}
