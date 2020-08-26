package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.SAVING_ACCOUNT_TRANSACTION_ID = getInternalColumnByName("savings_account_transaction_id");
        this.SAVING_ACCOUNT_CHARGE_ID = getInternalColumnByName("savings_account_charge_id");
        this.AMOUNT = getInternalColumnByName("amount");
    }

}
