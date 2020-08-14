package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.CHARGE_ID = getInternalColumnByName("charge_id");
        this.PENALTY = getInternalColumnByName("is_penalty");
        this.CHARGE_TIME_ENUM = getInternalColumnByName("charge_time_enum");
        this.CHARGE_DUE_DATE = getInternalColumnByName("charge_due_date");
        this.CHARGE_CALCULATION_ENUM = getInternalColumnByName("charge_calculation_enum");
        this.AMOUNT = getInternalColumnByName("amount");
        this.AMOUNT_PAID_DERIVED = getInternalColumnByName("amount_paid_derived");
        this.AMOUNT_WAIVED_DERIVED = getInternalColumnByName("amount_waived_derived");
        this.AMOUNT_WRITTEN_OFF_DERIVED = getInternalColumnByName("amount_writtenoff_derived");
        this.AMOUNT_OUTSTANDING_DERIVED = getInternalColumnByName("amount_outstanding_derived");
        this.PAID_DERIVED = getInternalColumnByName("is_paid_derived");
        this.WAIVED = getInternalColumnByName("waived");
        this.ACTIVE = getInternalColumnByName("is_active");
        this.IN_ACTIVATED_ON_DATE = getInternalColumnByName("inactivated_on_date");
    }

}
