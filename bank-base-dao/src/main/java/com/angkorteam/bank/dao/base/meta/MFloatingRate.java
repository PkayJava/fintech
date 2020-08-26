package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.BASE_LENDING_RATE = getInternalColumnByName("is_base_lending_rate");
        this.ACTIVE = getInternalColumnByName("is_active");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
    }

}
