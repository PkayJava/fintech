package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class GlimAccount extends AbstractTable {

    public final Column ID;

    public final Column GROUP_ID;

    public final Column ACCOUNT_NUMBER;

    public final Column PRINCIPAL_AMOUNT;

    public final Column CHILD_ACCOUNT_COUNT;

    public final Column ACCEPTING_CHILD;

    public final Column LOAN_STATUS_ID;

    public final Column APPLICATION_ID;

    public static GlimAccount staticInitialize(DataContext dataContext) {
        return new GlimAccount(dataContext);
    }

    private GlimAccount(DataContext dataContext) {
        super(dataContext, "glim_accounts");
        this.ID = getInternalColumnByName("id");
        this.GROUP_ID = getInternalColumnByName("group_id");
        this.ACCOUNT_NUMBER = getInternalColumnByName("account_number");
        this.PRINCIPAL_AMOUNT = getInternalColumnByName("principal_amount");
        this.CHILD_ACCOUNT_COUNT = getInternalColumnByName("child_accounts_count");
        this.ACCEPTING_CHILD = getInternalColumnByName("accepting_child");
        this.LOAN_STATUS_ID = getInternalColumnByName("loan_status_id");
        this.APPLICATION_ID = getInternalColumnByName("application_id");
    }

}
