package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class AccGlFinancialActivityAccount extends AbstractTable {

    public final Column ID;

    public final Column GL_ACCOUNT_ID;

    public final Column FINANCIAL_ACTIVITY_TYPE;

    public static AccGlFinancialActivityAccount staticInitialize(DataContext dataContext) {
        return new AccGlFinancialActivityAccount(dataContext);
    }

    private AccGlFinancialActivityAccount(DataContext dataContext) {
        super(dataContext, "acc_gl_financial_activity_account");
        this.ID = this.delegate.getColumnByName("id");
        this.GL_ACCOUNT_ID = this.delegate.getColumnByName("gl_account_id");
        this.FINANCIAL_ACTIVITY_TYPE = this.delegate.getColumnByName("financial_activity_type");
    }

}
