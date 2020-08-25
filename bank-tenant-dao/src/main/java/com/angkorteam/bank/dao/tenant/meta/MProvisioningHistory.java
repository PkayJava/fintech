package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MProvisioningHistory extends AbstractTable {

    public final Column ID;

    public final Column JOURNAL_ENTRY_CREATED;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public static MProvisioningHistory staticInitialize(DataContext dataContext) {
        return new MProvisioningHistory(dataContext);
    }

    private MProvisioningHistory(DataContext dataContext) {
        super(dataContext, "m_provisioning_history");
        this.ID = getInternalColumnByName("id");
        this.JOURNAL_ENTRY_CREATED = getInternalColumnByName("journal_entry_created");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
    }

}
