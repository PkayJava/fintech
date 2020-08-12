package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.GROUP_ID = this.delegate.getColumnByName("group_id");
        this.ACCOUNT_NUMBER = this.delegate.getColumnByName("account_number");
        this.PRINCIPAL_AMOUNT = this.delegate.getColumnByName("principal_amount");
        this.CHILD_ACCOUNT_COUNT = this.delegate.getColumnByName("child_accounts_count");
        this.ACCEPTING_CHILD = this.delegate.getColumnByName("accepting_child");
        this.LOAN_STATUS_ID = this.delegate.getColumnByName("loan_status_id");
        this.APPLICATION_ID = this.delegate.getColumnByName("application_id");
    }

}
