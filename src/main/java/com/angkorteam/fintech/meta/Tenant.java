package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class Tenant extends AbstractTable {

    public final Column ID;

    public final Column IDENTIFIER;

    public final Column NAME;

    public final Column TIMEZONE_ID;

    public final Column COUNTRY_ID;

    public final Column JOINED_DATE;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_DATE;

    public final Column OLTP_ID;

    public final Column REPORT_ID;

    public static Tenant staticInitialize(DataContext dataContext) {
        return new Tenant(dataContext);
    }

    private Tenant(DataContext dataContext) {
        super(dataContext, "tenants");
        this.ID = this.delegate.getColumnByName("id");
        this.IDENTIFIER = this.delegate.getColumnByName("identifier");
        this.NAME = this.delegate.getColumnByName("name");
        this.TIMEZONE_ID = this.delegate.getColumnByName("timezone_id");
        this.COUNTRY_ID = this.delegate.getColumnByName("country_id");
        this.JOINED_DATE = this.delegate.getColumnByName("joined_date");
        this.CREATED_DATE = this.delegate.getColumnByName("created_date");
        this.LAST_MODIFIED_DATE = this.delegate.getColumnByName("lastmodified_date");
        this.OLTP_ID = this.delegate.getColumnByName("oltp_id");
        this.REPORT_ID = this.delegate.getColumnByName("report_id");
    }

}
