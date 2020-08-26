package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.TEMPLATE_ID = getInternalColumnByName("template_id");
        this.ACTIVE = getInternalColumnByName("is_active");
        this.NAME = getInternalColumnByName("name");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
        this.UGD_TEMPLATE_ID = getInternalColumnByName("ugd_template_id");
    }

}
