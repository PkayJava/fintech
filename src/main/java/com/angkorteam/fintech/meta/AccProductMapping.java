package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class AccProductMapping extends AbstractTable {

    public final Column ID;

    public final Column GL_ACCOUNT_ID;

    public final Column PRODUCT_ID;

    public final Column PRODUCT_TYPE;

    public final Column PAYMENT_TYPE;

    public final Column CHARGE_ID;

    public final Column FINANCIAL_ACCOUNT_TYPE;

    public static AccProductMapping staticInitialize(DataContext dataContext) {
        return new AccProductMapping(dataContext);
    }

    private AccProductMapping(DataContext dataContext) {
        super(dataContext, "acc_product_mapping");
        this.ID = this.delegate.getColumnByName("id");
        this.GL_ACCOUNT_ID = this.delegate.getColumnByName("gl_account_id");
        this.PRODUCT_ID = this.delegate.getColumnByName("product_id");
        this.PRODUCT_TYPE = this.delegate.getColumnByName("product_type");
        this.PAYMENT_TYPE = this.delegate.getColumnByName("payment_type");
        this.CHARGE_ID = this.delegate.getColumnByName("charge_id");
        this.FINANCIAL_ACCOUNT_TYPE = this.delegate.getColumnByName("financial_account_type");
    }

}
