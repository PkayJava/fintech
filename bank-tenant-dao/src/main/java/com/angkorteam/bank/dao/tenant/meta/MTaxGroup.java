package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MTaxGroup extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public static MTaxGroup staticInitialize(DataContext dataContext) {
        return new MTaxGroup(dataContext);
    }

    private MTaxGroup(DataContext dataContext) {
        super(dataContext, "m_tax_group");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
    }

}
