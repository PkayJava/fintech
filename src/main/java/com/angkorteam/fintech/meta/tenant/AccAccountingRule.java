package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class AccAccountingRule extends AbstractTable {

    public final Column ID;

    public final Column OFFICE_ID;

    public final Column NAME;

    public final Column DEBIT_ACCOUNT_ID;

    public final Column ALLOW_MULTIPLE_DEBIT;

    public final Column CREDIT_ACCOUNT_ID;

    public final Column ALLOW_MULTIPLE_CREDIT;

    public final Column DESCRIPTION;

    public final Column SYSTEM_DEFINED;

    public static AccAccountingRule staticInitialize(DataContext dataContext) {
        return new AccAccountingRule(dataContext);
    }

    private AccAccountingRule(DataContext dataContext) {
        super(dataContext, "acc_accounting_rule");
        this.ID = getInternalColumnByName("id");
        this.OFFICE_ID = getInternalColumnByName("office_id");
        this.NAME = getInternalColumnByName("name");
        this.DEBIT_ACCOUNT_ID = getInternalColumnByName("debit_account_id");
        this.ALLOW_MULTIPLE_DEBIT = getInternalColumnByName("allow_multiple_debits");
        this.CREDIT_ACCOUNT_ID = getInternalColumnByName("credit_account_id");
        this.ALLOW_MULTIPLE_CREDIT = getInternalColumnByName("allow_multiple_credits");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.SYSTEM_DEFINED = getInternalColumnByName("system_defined");
    }

}
