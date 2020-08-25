package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MShareProductMarketPrice extends AbstractTable {

    public final Column ID;

    public final Column PRODUCT_ID;

    public final Column FROM_DATE;

    public final Column SHARE_VALUE;

    public static MShareProductMarketPrice staticInitialize(DataContext dataContext) {
        return new MShareProductMarketPrice(dataContext);
    }

    private MShareProductMarketPrice(DataContext dataContext) {
        super(dataContext, "m_share_product_market_price");
        this.ID = getInternalColumnByName("id");
        this.PRODUCT_ID = getInternalColumnByName("product_id");
        this.FROM_DATE = getInternalColumnByName("from_date");
        this.SHARE_VALUE = getInternalColumnByName("share_value");
    }

}
