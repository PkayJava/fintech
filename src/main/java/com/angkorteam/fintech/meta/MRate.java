package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
        this.PERCENTAGE = this.delegate.getColumnByName("percentage");
        this.ACTIVE = this.delegate.getColumnByName("active");
        this.PRODUCT_APPLY = this.delegate.getColumnByName("product_apply");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
        this.APPROVE_USER = this.delegate.getColumnByName("approve_user");
    }

}
