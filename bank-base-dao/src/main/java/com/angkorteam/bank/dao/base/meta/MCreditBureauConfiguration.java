package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.CONFIG_KEY = getInternalColumnByName("configkey");
        this.VALUE = getInternalColumnByName("value");
        this.ORGANISATION_CREDIT_BUREAU_ID = getInternalColumnByName("organisation_creditbureau_id");
        this.DESCRIPTION = getInternalColumnByName("description");
    }

}
