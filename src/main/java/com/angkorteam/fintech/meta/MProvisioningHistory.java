package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.JOURNAL_ENTRY_CREATED = this.delegate.getColumnByName("journal_entry_created");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
    }

}
