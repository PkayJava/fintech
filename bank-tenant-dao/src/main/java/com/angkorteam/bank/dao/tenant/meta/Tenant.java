package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.IDENTIFIER = getInternalColumnByName("identifier");
        this.NAME = getInternalColumnByName("name");
        this.TIMEZONE_ID = getInternalColumnByName("timezone_id");
        this.COUNTRY_ID = getInternalColumnByName("country_id");
        this.JOINED_DATE = getInternalColumnByName("joined_date");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
        this.OLTP_ID = getInternalColumnByName("oltp_id");
        this.REPORT_ID = getInternalColumnByName("report_id");
    }

}
