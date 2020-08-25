package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSavingProductCharge extends AbstractTable {

    public final Column SAVING_PRODUCT_ID;

    public final Column CHARGE_ID;

    public static MSavingProductCharge staticInitialize(DataContext dataContext) {
        return new MSavingProductCharge(dataContext);
    }

    private MSavingProductCharge(DataContext dataContext) {
        super(dataContext, "m_savings_product_charge");
        this.SAVING_PRODUCT_ID = getInternalColumnByName("savings_product_id");
        this.CHARGE_ID = getInternalColumnByName("charge_id");
    }

}
