package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MTaxGroupMapping extends AbstractTable {

    public final Column ID;

    public final Column TAX_GROUP_ID;

    public final Column TAX_COMPONENT_ID;

    public final Column START_DATE;

    public final Column END_DATE;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public static MTaxGroupMapping staticInitialize(DataContext dataContext) {
        return new MTaxGroupMapping(dataContext);
    }

    private MTaxGroupMapping(DataContext dataContext) {
        super(dataContext, "m_tax_group_mappings");
        this.ID = getInternalColumnByName("id");
        this.TAX_GROUP_ID = getInternalColumnByName("tax_group_id");
        this.TAX_COMPONENT_ID = getInternalColumnByName("tax_component_id");
        this.START_DATE = getInternalColumnByName("start_date");
        this.END_DATE = getInternalColumnByName("end_date");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
    }

}
