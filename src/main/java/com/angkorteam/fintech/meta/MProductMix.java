package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MProductMix extends AbstractTable {

    public final Column ID;

    public final Column PRODUCT_ID;

    public final Column RESTRICTED_PRODUCT_ID;

    public static MProductMix staticInitialize(DataContext dataContext) {
        return new MProductMix(dataContext);
    }

    private MProductMix(DataContext dataContext) {
        super(dataContext, "m_product_mix");
        this.ID = this.delegate.getColumnByName("id");
        this.PRODUCT_ID = this.delegate.getColumnByName("product_id");
        this.RESTRICTED_PRODUCT_ID = this.delegate.getColumnByName("restricted_product_id");
    }

}
