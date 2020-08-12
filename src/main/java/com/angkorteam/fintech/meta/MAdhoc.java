package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
        this.QUERY = this.delegate.getColumnByName("query");
        this.TABLE_NAME = this.delegate.getColumnByName("table_name");
        this.TABLE_FIELD = this.delegate.getColumnByName("table_fields");
        this.EMAIL = this.delegate.getColumnByName("email");
        this.ACTIVE = this.delegate.getColumnByName("IsActive");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.CREATED_BY_ID = this.delegate.getColumnByName("createdby_id");
        this.LAST_MODIFIED_BY_ID = this.delegate.getColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
        this.REPORT_RUN_FREQUENCY_CODE = this.delegate.getColumnByName("report_run_frequency_code");
        this.REPORT_RUN_EVERY = this.delegate.getColumnByName("report_run_every");
        this.LAST_RUN = this.delegate.getColumnByName("last_run");
    }

}
