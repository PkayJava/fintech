package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MCreditBureauConfiguration extends AbstractTable {

    public final Column ID;

    public final Column CONFIG_KEY;

    public final Column VALUE;

    public final Column ORGANISATION_CREDIT_BUREAU_ID;

    public final Column DESCRIPTION;

    public static MCreditBureauConfiguration staticInitialize(DataContext dataContext) {
        return new MCreditBureauConfiguration(dataContext);
    }

    private MCreditBureauConfiguration(DataContext dataContext) {
        super(dataContext, "m_creditbureau_configuration");
        this.ID = this.delegate.getColumnByName("id");
        this.CONFIG_KEY = this.delegate.getColumnByName("configkey");
        this.VALUE = this.delegate.getColumnByName("value");
        this.ORGANISATION_CREDIT_BUREAU_ID = this.delegate.getColumnByName("organisation_creditbureau_id");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
    }

}
