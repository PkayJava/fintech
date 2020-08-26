package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MShareAccountChargePaidBy extends AbstractTable {

    public final Column ID;

    public final Column SHARE_TRANSACTION_ID;

    public final Column CHARGE_TRANSACTION_ID;

    public final Column AMOUNT;

    public static MShareAccountChargePaidBy staticInitialize(DataContext dataContext) {
        return new MShareAccountChargePaidBy(dataContext);
    }

    private MShareAccountChargePaidBy(DataContext dataContext) {
        super(dataContext, "m_share_account_charge_paid_by");
        this.ID = getInternalColumnByName("id");
        this.SHARE_TRANSACTION_ID = getInternalColumnByName("share_transaction_id");
        this.CHARGE_TRANSACTION_ID = getInternalColumnByName("charge_transaction_id");
        this.AMOUNT = getInternalColumnByName("amount");
    }

}
