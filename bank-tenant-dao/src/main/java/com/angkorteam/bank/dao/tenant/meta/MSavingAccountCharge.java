package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSavingAccountCharge extends AbstractTable {

    public final Column ID;

    public final Column SAVING_ACCOUNT_ID;

    public final Column CHARGE_ID;

    public final Column PENALTY;

    public final Column CHARGE_TIME_ENUM;

    public final Column CHARGE_DUE_DATE;

    public final Column FEE_ON_MONTH;

    public final Column FEE_ON_DAY;

    public final Column FEE_INTERVAL;

    public final Column CHARGE_CALCULATION_ENUM;

    public final Column CALCULATION_PERCENTAGE;

    public final Column CALCULATION_ON_AMOUNT;

    public final Column AMOUNT;

    public final Column AMOUNT_PAID_DERIVED;

    public final Column AMOUNT_WAIVED_DERIVED;

    public final Column AMOUNT_WRITTEN_OFF_DERIVED;

    public final Column AMOUNT_OUTSTANDING_DERIVED;

    public final Column PAID_DERIVED;

    public final Column WAIVED;

    public final Column ACTIVE;

    public final Column INACTIVATED_ON_DATE;

    public static MSavingAccountCharge staticInitialize(DataContext dataContext) {
        return new MSavingAccountCharge(dataContext);
    }

    private MSavingAccountCharge(DataContext dataContext) {
        super(dataContext, "m_savings_account_charge");
        this.ID = getInternalColumnByName("id");
        this.SAVING_ACCOUNT_ID = getInternalColumnByName("savings_account_id");
        this.CHARGE_ID = getInternalColumnByName("charge_id");
        this.PENALTY = getInternalColumnByName("is_penalty");
        this.CHARGE_TIME_ENUM = getInternalColumnByName("charge_time_enum");
        this.CHARGE_DUE_DATE = getInternalColumnByName("charge_due_date");
        this.FEE_ON_MONTH = getInternalColumnByName("fee_on_month");
        this.FEE_ON_DAY = getInternalColumnByName("fee_on_day");
        this.FEE_INTERVAL = getInternalColumnByName("fee_interval");
        this.CHARGE_CALCULATION_ENUM = getInternalColumnByName("charge_calculation_enum");
        this.CALCULATION_PERCENTAGE = getInternalColumnByName("calculation_percentage");
        this.CALCULATION_ON_AMOUNT = getInternalColumnByName("calculation_on_amount");
        this.AMOUNT = getInternalColumnByName("amount");
        this.AMOUNT_PAID_DERIVED = getInternalColumnByName("amount_paid_derived");
        this.AMOUNT_WAIVED_DERIVED = getInternalColumnByName("amount_waived_derived");
        this.AMOUNT_WRITTEN_OFF_DERIVED = getInternalColumnByName("amount_writtenoff_derived");
        this.AMOUNT_OUTSTANDING_DERIVED = getInternalColumnByName("amount_outstanding_derived");
        this.PAID_DERIVED = getInternalColumnByName("is_paid_derived");
        this.WAIVED = getInternalColumnByName("waived");
        this.ACTIVE = getInternalColumnByName("is_active");
        this.INACTIVATED_ON_DATE = getInternalColumnByName("inactivated_on_date");
    }

}
