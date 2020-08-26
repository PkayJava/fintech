package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.*;

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
        this.ID = getInternalColumnByName("id");
        this.GROUP_ID = getInternalColumnByName("group_id");
        this.ACCOUNT_NUMBER = getInternalColumnByName("account_number");
        this.CHILD_ACCOUNT_COUNT = getInternalColumnByName("child_accounts_count");
        this.ACCEPTING_CHILD = getInternalColumnByName("accepting_child");
        this.APPLICATION_ID = getInternalColumnByName("application_id");
        this.PARENT_DEPOSIT = getInternalColumnByName("parent_deposit");
        this.SAVING_STATUS_ID = getInternalColumnByName("savings_status_id");
    }

}
