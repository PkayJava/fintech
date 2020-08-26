package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.CRITERIA_NAME = getInternalColumnByName("criteria_name");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
    }

}
