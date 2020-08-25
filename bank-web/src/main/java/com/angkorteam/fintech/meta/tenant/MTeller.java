package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MTeller extends AbstractTable {

    public final Column ID;

    public final Column OFFICE_ID;

    public final Column DEBIT_ACCOUNT_ID;

    public final Column CREDIT_ACCOUNT_ID;

    public final Column NAME;

    public final Column DESCRIPTION;

    public final Column VALID_FROM;

    public final Column VALID_TO;

    public final Column STATE;

    public static MTeller staticInitialize(DataContext dataContext) {
        return new MTeller(dataContext);
    }

    private MTeller(DataContext dataContext) {
        super(dataContext, "m_tellers");
        this.ID = getInternalColumnByName("id");
        this.OFFICE_ID = getInternalColumnByName("office_id");
        this.DEBIT_ACCOUNT_ID = getInternalColumnByName("debit_account_id");
        this.CREDIT_ACCOUNT_ID = getInternalColumnByName("credit_account_id");
        this.NAME = getInternalColumnByName("name");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.VALID_FROM = getInternalColumnByName("valid_from");
        this.VALID_TO = getInternalColumnByName("valid_to");
        this.STATE = getInternalColumnByName("state");
    }

}
