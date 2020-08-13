package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MHook extends AbstractTable {

    public final Column ID;

    public final Column TEMPLATE_ID;

    public final Column ACTIVE;

    public final Column NAME;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public final Column UGD_TEMPLATE_ID;

    public static MHook staticInitialize(DataContext dataContext) {
        return new MHook(dataContext);
    }

    private MHook(DataContext dataContext) {
        super(dataContext, "m_hook");
        this.ID = this.delegate.getColumnByName("id");
        this.TEMPLATE_ID = this.delegate.getColumnByName("template_id");
        this.ACTIVE = this.delegate.getColumnByName("is_active");
        this.NAME = this.delegate.getColumnByName("name");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
        this.UGD_TEMPLATE_ID = this.delegate.getColumnByName("ugd_template_id");
    }

}
