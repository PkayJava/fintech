package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanTopup extends AbstractTable {

    public final Column ID;

    public final Column LOAN_ID;

    public final Column CLOSURE_LOAN_ID;

    public final Column ACCOUNT_TRANSFER_DETAILS_ID;

    public final Column TOPUP_AMOUNT;

    public static MLoanTopup staticInitialize(DataContext dataContext) {
        return new MLoanTopup(dataContext);
    }

    private MLoanTopup(DataContext dataContext) {
        super(dataContext, "m_loan_topup");
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_ID = this.delegate.getColumnByName("loan_id");
        this.CLOSURE_LOAN_ID = this.delegate.getColumnByName("closure_loan_id");
        this.ACCOUNT_TRANSFER_DETAILS_ID = this.delegate.getColumnByName("account_transfer_details_id");
        this.TOPUP_AMOUNT = this.delegate.getColumnByName("topup_amount");
    }

}
