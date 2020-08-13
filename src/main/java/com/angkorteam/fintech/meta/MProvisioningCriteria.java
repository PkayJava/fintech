package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MProvisioningCriteria extends AbstractTable {

    public final Column ID;

    public final Column CRITERIA_NAME;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public static MProvisioningCriteria staticInitialize(DataContext dataContext) {
        return new MProvisioningCriteria(dataContext);
    }

    private MProvisioningCriteria(DataContext dataContext) {
        super(dataContext, "m_provisioning_criteria");
        this.ID = this.delegate.getColumnByName("id");
        this.CRITERIA_NAME = this.delegate.getColumnByName("criteria_name");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
    }

}
