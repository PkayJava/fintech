package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSavingAccountChargePaidBy extends AbstractTable {

    public final Column ID;

    public final Column SAVING_ACCOUNT_TRANSACTION_ID;

    public final Column SAVING_ACCOUNT_CHARGE_ID;

    public final Column AMOUNT;

    public static MSavingAccountChargePaidBy staticInitialize(DataContext dataContext) {
        return new MSavingAccountChargePaidBy(dataContext);
    }

    private MSavingAccountChargePaidBy(DataContext dataContext) {
        super(dataContext, "m_savings_account_charge_paid_by");
        this.ID = this.delegate.getColumnByName("id");
        this.SAVING_ACCOUNT_TRANSACTION_ID = this.delegate.getColumnByName("savings_account_transaction_id");
        this.SAVING_ACCOUNT_CHARGE_ID = this.delegate.getColumnByName("savings_account_charge_id");
        this.AMOUNT = this.delegate.getColumnByName("amount");
    }

}
