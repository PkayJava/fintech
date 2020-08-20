package com.angkorteam.fintech.meta;

import com.angkorteam.fintech.meta.tenant.AccAccountingRule;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class SchemaVersion extends AbstractTable {

    public final Column INSTALLED_RANK;

    public final Column VERSION;

    public final Column DESCRIPTION;

    public final Column TYPE;

    public final Column SCRIPT;

    public final Column CHECKSUM;

    public final Column INSTALLED_BY;

    public final Column INSTALLED_ON;

    public final Column EXECUTION_TIME;

    public final Column SUCCESS;

    public static SchemaVersion staticInitialize(DataContext dataContext) {
        return new SchemaVersion(dataContext);
    }

    private SchemaVersion(DataContext dataContext) {
        super(dataContext, "schema_version");
        this.INSTALLED_RANK = this.delegate.getColumnByName("installed_rank");
        this.VERSION = this.delegate.getColumnByName("version");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.TYPE = this.delegate.getColumnByName("type");
        this.SCRIPT = this.delegate.getColumnByName("script");
        this.CHECKSUM = this.delegate.getColumnByName("checksum");
        this.INSTALLED_BY = this.delegate.getColumnByName("installed_by");
        this.INSTALLED_ON = this.delegate.getColumnByName("installed_on");
        this.EXECUTION_TIME = this.delegate.getColumnByName("execution_time");
        this.SUCCESS = this.delegate.getColumnByName("success");
    }

}
