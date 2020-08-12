package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
        this.PRODUCT = this.delegate.getColumnByName("product");
        this.COUNTRY = this.delegate.getColumnByName("country");
        this.IMPLEMENTATION_KEY = this.delegate.getColumnByName("implementationKey");
    }

}
