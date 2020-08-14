package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MClientChargePaidBy extends AbstractTable {

    public final Column ID;

    public final Column CLIENT_TRANSACTION_ID;

    public final Column CLIENT_CHARGE_ID;

    public final Column AMOUNT;

    public static MClientChargePaidBy staticInitialize(DataContext dataContext) {
        return new MClientChargePaidBy(dataContext);
    }

    private MClientChargePaidBy(DataContext dataContext) {
        super(dataContext, "m_client_charge_paid_by");
        this.ID = getInternalColumnByName("id");
        this.CLIENT_TRANSACTION_ID = getInternalColumnByName("client_transaction_id");
        this.CLIENT_CHARGE_ID = getInternalColumnByName("client_charge_id");
        this.AMOUNT = getInternalColumnByName("amount");
    }

}
