package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MTrialBalance extends AbstractTable {

    public final Column OFFICE_ID;

    public final Column ACCOUNT_ID;

    public final Column AMOUNT;

    public final Column ENTRY_DATE;

    public final Column CREATED_DATE;

    public final Column CLOSING_BALANCE;

    public static MTrialBalance staticInitialize(DataContext dataContext) {
        return new MTrialBalance(dataContext);
    }

    private MTrialBalance(DataContext dataContext) {
        super(dataContext, "m_trial_balance");
        this.OFFICE_ID = getInternalColumnByName("office_id");
        this.ACCOUNT_ID = getInternalColumnByName("account_id");
        this.AMOUNT = getInternalColumnByName("amount");
        this.ENTRY_DATE = getInternalColumnByName("entry_date");
        this.CREATED_DATE = getInternalColumnByName("created_date");
        this.CLOSING_BALANCE = getInternalColumnByName("closing_balance");
    }

}
