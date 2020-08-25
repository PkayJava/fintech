package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MRate extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column PERCENTAGE;

    public final Column ACTIVE;

    public final Column PRODUCT_APPLY;

    public final Column CREATED_DATE;

    public final Column CREATED_BY_ID;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public final Column APPROVE_USER;

    public static MRate staticInitialize(DataContext dataContext) {
        return new MRate(dataContext);
    }

    private MRate(DataContext dataContext) {
        super(dataContext, "m_rate");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.PERCENTAGE = getInternalColumnByName("percentage");
        this.ACTIVE = getInternalColumnByName("active");
        this.PRODUCT_APPLY = getInternalColumnByName("product_apply");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
        this.APPROVE_USER = getInternalColumnByName("approve_user");
    }

}
