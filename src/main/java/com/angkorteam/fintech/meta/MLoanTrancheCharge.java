package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanTrancheCharge extends AbstractTable {

    public final Column ID;

    public final Column LOAN_ID;

    public final Column CHARGE_ID;

    public static MLoanTrancheCharge staticInitialize(DataContext dataContext) {
        return new MLoanTrancheCharge(dataContext);
    }

    private MLoanTrancheCharge(DataContext dataContext) {
        super(dataContext, "m_loan_tranche_charges");
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_ID = this.delegate.getColumnByName("loan_id");
        this.CHARGE_ID = this.delegate.getColumnByName("charge_id");
    }

}
