package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MCreditBureau extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column PRODUCT;

    public final Column COUNTRY;

    public final Column IMPLEMENTATION_KEY;

    public static MCreditBureau staticInitialize(DataContext dataContext) {
        return new MCreditBureau(dataContext);
    }

    private MCreditBureau(DataContext dataContext) {
        super(dataContext, "m_creditbureau");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.PRODUCT = getInternalColumnByName("product");
        this.COUNTRY = getInternalColumnByName("country");
        this.IMPLEMENTATION_KEY = getInternalColumnByName("implementationKey");
    }

}
