package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MAdhoc extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column QUERY;

    public final Column TABLE_NAME;

    public final Column TABLE_FIELD;

    public final Column EMAIL;

    public final Column ACTIVE;

    public final Column CREATED_DATE;

    public final Column CREATED_BY_ID;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public final Column REPORT_RUN_FREQUENCY_CODE;

    public final Column REPORT_RUN_EVERY;

    public final Column LAST_RUN;

    public static MAdhoc staticInitialize(DataContext dataContext) {
        return new MAdhoc(dataContext);
    }

    private MAdhoc(DataContext dataContext) {
        super(dataContext, "m_adhoc");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.QUERY = getInternalColumnByName("query");
        this.TABLE_NAME = getInternalColumnByName("table_name");
        this.TABLE_FIELD = getInternalColumnByName("table_fields");
        this.EMAIL = getInternalColumnByName("email");
        this.ACTIVE = getInternalColumnByName("IsActive");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
        this.REPORT_RUN_FREQUENCY_CODE = getInternalColumnByName("report_run_frequency_code");
        this.REPORT_RUN_EVERY = getInternalColumnByName("report_run_every");
        this.LAST_RUN = getInternalColumnByName("last_run");
    }

}
