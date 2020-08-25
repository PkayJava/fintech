package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MTaxComponent extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column PERCENTAGE;

    public final Column DEBIT_ACCOUNT_TYPE_ENUM;

    public final Column DEBIT_ACCOUNT_ID;

    public final Column CREDIT_ACCOUNT_TYPE_ENUM;

    public final Column CREDIT_ACCOUNT_ID;

    public final Column START_DATE;

    public final Column CREATED_BY_ID;

    public final Column CREATED_DATE;

    public final Column LAST_MODIFIED_BY_ID;

    public final Column LAST_MODIFIED_DATE;

    public static MTaxComponent staticInitialize(DataContext dataContext) {
        return new MTaxComponent(dataContext);
    }

    private MTaxComponent(DataContext dataContext) {
        super(dataContext, "m_tax_component");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.PERCENTAGE = getInternalColumnByName("percentage");
        this.DEBIT_ACCOUNT_TYPE_ENUM = getInternalColumnByName("debit_account_type_enum");
        this.DEBIT_ACCOUNT_ID = getInternalColumnByName("debit_account_id");
        this.CREDIT_ACCOUNT_TYPE_ENUM = getInternalColumnByName("credit_account_type_enum");
        this.CREDIT_ACCOUNT_ID = getInternalColumnByName("credit_account_id");
        this.START_DATE = getInternalColumnByName("start_date");
        this.CREATED_BY_ID = getInternalColumnByName("createdby_id");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.LAST_MODIFIED_BY_ID = getInternalColumnByName("lastmodifiedby_id");
        this.LAST_MODIFIED_DATE = getInternalColumnByName("lastmodified_date");
    }

}
