package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MClientCharge extends AbstractTable {

    public final Column ID;

    public final Column CLIENT_ID;

    public final Column CHARGE_ID;

    public final Column PENALTY;

    public final Column CHARGE_TIME_ENUM;

    public final Column CHARGE_DUE_DATE;

    public final Column CHARGE_CALCULATION_ENUM;

    public final Column AMOUNT;

    public final Column AMOUNT_PAID_DERIVED;

    public final Column AMOUNT_WAIVED_DERIVED;

    public final Column AMOUNT_WRITTEN_OFF_DERIVED;

    public final Column AMOUNT_OUTSTANDING_DERIVED;

    public final Column PAID_DERIVED;

    public final Column WAIVED;

    public final Column ACTIVE;

    public final Column IN_ACTIVATED_ON_DATE;

    public static MClientCharge staticInitialize(DataContext dataContext) {
        return new MClientCharge(dataContext);
    }

    private MClientCharge(DataContext dataContext) {
        super(dataContext, "m_client_charge");
        this.ID = this.delegate.getColumnByName("id");
        this.CLIENT_ID = this.delegate.getColumnByName("client_id");
        this.CHARGE_ID = this.delegate.getColumnByName("charge_id");
        this.PENALTY = this.delegate.getColumnByName("is_penalty");
        this.CHARGE_TIME_ENUM = this.delegate.getColumnByName("charge_time_enum");
        this.CHARGE_DUE_DATE = this.delegate.getColumnByName("charge_due_date");
        this.CHARGE_CALCULATION_ENUM = this.delegate.getColumnByName("charge_calculation_enum");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.AMOUNT_PAID_DERIVED = this.delegate.getColumnByName("amount_paid_derived");
        this.AMOUNT_WAIVED_DERIVED = this.delegate.getColumnByName("amount_waived_derived");
        this.AMOUNT_WRITTEN_OFF_DERIVED = this.delegate.getColumnByName("amount_writtenoff_derived");
        this.AMOUNT_OUTSTANDING_DERIVED = this.delegate.getColumnByName("amount_outstanding_derived");
        this.PAID_DERIVED = this.delegate.getColumnByName("is_paid_derived");
        this.WAIVED = this.delegate.getColumnByName("waived");
        this.ACTIVE = this.delegate.getColumnByName("is_active");
        this.IN_ACTIVATED_ON_DATE = this.delegate.getColumnByName("inactivated_on_date");
    }

}
