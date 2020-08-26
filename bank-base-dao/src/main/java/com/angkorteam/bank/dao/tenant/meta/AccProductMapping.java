package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.GL_ACCOUNT_ID = getInternalColumnByName("gl_account_id");
        this.PRODUCT_ID = getInternalColumnByName("product_id");
        this.PRODUCT_TYPE = getInternalColumnByName("product_type");
        this.PAYMENT_TYPE = getInternalColumnByName("payment_type");
        this.CHARGE_ID = getInternalColumnByName("charge_id");
        this.FINANCIAL_ACCOUNT_TYPE = getInternalColumnByName("financial_account_type");
    }

}
