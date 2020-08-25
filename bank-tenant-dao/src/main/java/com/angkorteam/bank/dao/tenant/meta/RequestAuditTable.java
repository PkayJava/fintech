package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class RequestAuditTable extends AbstractTable {

    public final Column ID;

    public final Column LAST_NAME;

    public final Column USERNAME;

    public final Column MOBILE_NUMBER;

    public final Column FIRST_NAME;

    public final Column AUTHENTICATION_TOKEN;

    public final Column PASSWORD;

    public final Column EMAIL;

    public final Column CLIENT_ID;

    public final Column CREATED_DATE;

    public final Column ACCOUNT_NUMBER;

    public static RequestAuditTable staticInitialize(DataContext dataContext) {
        return new RequestAuditTable(dataContext);
    }

    private RequestAuditTable(DataContext dataContext) {
        super(dataContext, "request_audit_table");
        this.ID = getInternalColumnByName("id");
        this.LAST_NAME = getInternalColumnByName("lastname");
        this.USERNAME = getInternalColumnByName("username");
        this.MOBILE_NUMBER = getInternalColumnByName("mobile_number");
        this.FIRST_NAME = getInternalColumnByName("firstname");
        this.AUTHENTICATION_TOKEN = getInternalColumnByName("authentication_token");
        this.PASSWORD = getInternalColumnByName("password");
        this.EMAIL = getInternalColumnByName("email");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.ACCOUNT_NUMBER = getInternalColumnByName("account_number");
    }

}
