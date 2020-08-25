package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MTaxComponentHistory extends AbstractTable {

    public final Column ID;

    public final Column TAX_COMPONENT_ID;

    public final Column PERCENTAGE;

    public final Column START_DATE;

    public final Column END_DATE;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public static MTaxComponentHistory staticInitialize(DataContext dataContext) {
        return new MTaxComponentHistory(dataContext);
    }

    private MTaxComponentHistory(DataContext dataContext) {
        super(dataContext, "m_tax_component_history");
        this.ID = getInternalColumnByName("id");
        this.TAX_COMPONENT_ID = getInternalColumnByName("tax_component_id");
        this.PERCENTAGE = getInternalColumnByName("percentage");
        this.START_DATE = getInternalColumnByName("start_date");
        this.END_DATE = getInternalColumnByName("end_date");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
    }

}
