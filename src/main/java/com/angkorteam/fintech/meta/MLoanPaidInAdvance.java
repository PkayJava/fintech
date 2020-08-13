package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanPaidInAdvance extends AbstractTable {

    public final Column LOAN_ID;

    public final Column PRINCIPAL_IN_ADVANCE_DERIVED;

    public final Column INTEREST_IN_ADVANCE_DERIVED;

    public final Column FEE_CHARGE_IN_ADVANCE_DERIVED;

    public final Column PENALTY_CHARGE_IN_ADVANCE_DERIVED;

    public final Column TOTAL_IN_ADVANCE_DERIVED;

    public static MLoanPaidInAdvance staticInitialize(DataContext dataContext) {
        return new MLoanPaidInAdvance(dataContext);
    }

    private MLoanPaidInAdvance(DataContext dataContext) {
        super(dataContext, "m_loan_paid_in_advance");
        this.LOAN_ID = this.delegate.getColumnByName("loan_id");
        this.PRINCIPAL_IN_ADVANCE_DERIVED = this.delegate.getColumnByName("principal_in_advance_derived");
        this.INTEREST_IN_ADVANCE_DERIVED = this.delegate.getColumnByName("interest_in_advance_derived");
        this.FEE_CHARGE_IN_ADVANCE_DERIVED = this.delegate.getColumnByName("fee_charges_in_advance_derived");
        this.PENALTY_CHARGE_IN_ADVANCE_DERIVED = this.delegate.getColumnByName("penalty_charges_in_advance_derived");
        this.TOTAL_IN_ADVANCE_DERIVED = this.delegate.getColumnByName("total_in_advance_derived");
    }

}
