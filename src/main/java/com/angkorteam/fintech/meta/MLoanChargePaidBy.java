package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MLoanChargePaidBy extends AbstractTable {

    public final Column ID;

    public final Column LOAN_TRANSACTION_ID;

    public final Column LOAN_CHARGE_ID;

    public final Column AMOUNT;

    public final Column INSTALLMENT_NUMBER;

    public static MLoanChargePaidBy staticInitialize(DataContext dataContext) {
        return new MLoanChargePaidBy(dataContext);
    }

    private MLoanChargePaidBy(DataContext dataContext) {
        super(dataContext, "m_loan_charge_paid_by");
        this.ID = this.delegate.getColumnByName("id");
        this.LOAN_TRANSACTION_ID = this.delegate.getColumnByName("loan_transaction_id");
        this.LOAN_CHARGE_ID = this.delegate.getColumnByName("loan_charge_id");
        this.AMOUNT = this.delegate.getColumnByName("amount");
        this.INSTALLMENT_NUMBER = this.delegate.getColumnByName("installment_number");
    }

}
