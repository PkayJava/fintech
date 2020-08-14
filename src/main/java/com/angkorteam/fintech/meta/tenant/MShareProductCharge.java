package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MShareProductCharge extends AbstractTable {

    public final Column PRODUCT_ID;

    public final Column CHARGE_ID;

    public static MShareProductCharge staticInitialize(DataContext dataContext) {
        return new MShareProductCharge(dataContext);
    }

    private MShareProductCharge(DataContext dataContext) {
        super(dataContext, "m_share_product_charge");
        this.PRODUCT_ID = getInternalColumnByName("product_id");
        this.CHARGE_ID = getInternalColumnByName("charge_id");
    }

}
