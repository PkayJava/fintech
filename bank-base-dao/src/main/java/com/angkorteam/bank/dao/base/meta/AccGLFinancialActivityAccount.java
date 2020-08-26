package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class AccGLFinancialActivityAccount extends AbstractTable {

    public final Column ID;

    public final Column GL_ACCOUNT_ID;

    public final Column FINANCIAL_ACTIVITY_TYPE;

    public static AccGLFinancialActivityAccount staticInitialize(DataContext dataContext) {
        return new AccGLFinancialActivityAccount(dataContext);
    }

    private AccGLFinancialActivityAccount(DataContext dataContext) {
        super(dataContext, "acc_gl_financial_activity_account");
        this.ID = getInternalColumnByName("id");
        this.GL_ACCOUNT_ID = getInternalColumnByName("gl_account_id");
        this.FINANCIAL_ACTIVITY_TYPE = getInternalColumnByName("financial_activity_type");
    }

}
