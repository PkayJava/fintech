package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.OFFICE_ID = this.delegate.getColumnByName("office_id");
        this.NAME = this.delegate.getColumnByName("name");
        this.DEBIT_ACCOUNT_ID = this.delegate.getColumnByName("debit_account_id");
        this.ALLOW_MULTIPLE_DEBIT = this.delegate.getColumnByName("allow_multiple_debits");
        this.CREDIT_ACCOUNT_ID = this.delegate.getColumnByName("credit_account_id");
        this.ALLOW_MULTIPLE_CREDIT = this.delegate.getColumnByName("allow_multiple_credits");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.SYSTEM_DEFINED = this.delegate.getColumnByName("system_defined");
    }

}
