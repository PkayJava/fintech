package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.*;

import java.util.Collection;
import java.util.List;

public class GsimAccount extends AbstractTable {

    public final Column ID;

    public final Column GROUP_ID;

    public final Column ACCOUNT_NUMBER;

    public final Column PARENT_DEPOSIT;

    public final Column CHILD_ACCOUNT_COUNT;

    public final Column ACCEPTING_CHILD;

    public final Column SAVING_STATUS_ID;

    public final Column APPLICATION_ID;

    public static GsimAccount staticInitialize(DataContext dataContext) {
        return new GsimAccount(dataContext);
    }

    private GsimAccount(DataContext dataContext) {
        super(dataContext, "gsim_accounts");
        this.ID = this.delegate.getColumnByName("id");
        this.GROUP_ID = this.delegate.getColumnByName("group_id");
        this.ACCOUNT_NUMBER = this.delegate.getColumnByName("account_number");
        this.CHILD_ACCOUNT_COUNT = this.delegate.getColumnByName("child_accounts_count");
        this.ACCEPTING_CHILD = this.delegate.getColumnByName("accepting_child");
        this.APPLICATION_ID = this.delegate.getColumnByName("application_id");
        this.PARENT_DEPOSIT = this.delegate.getColumnByName("parent_deposit");
        this.SAVING_STATUS_ID = this.delegate.getColumnByName("savings_status_id");
    }

}
