package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MFloatingRate extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column BASE_LENDING_RATE;

    public final Column ACTIVE;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public static MFloatingRate staticInitialize(DataContext dataContext) {
        return new MFloatingRate(dataContext);
    }

    private MFloatingRate(DataContext dataContext) {
        super(dataContext, "m_floating_rates");
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
        this.BASE_LENDING_RATE = this.delegate.getColumnByName("is_base_lending_rate");
        this.ACTIVE = this.delegate.getColumnByName("is_active");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
    }

}
