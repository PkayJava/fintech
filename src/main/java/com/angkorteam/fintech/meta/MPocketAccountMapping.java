package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MPocketAccountMapping extends AbstractTable {

    public final Column ID;

    public final Column POCKET_ID;

    public final Column ACCOUNT_ID;

    public final Column ACCOUNT_TYPE;

    public final Column ACCOUNT_NUMBER;

    public static MPocketAccountMapping staticInitialize(DataContext dataContext) {
        return new MPocketAccountMapping(dataContext);
    }

    private MPocketAccountMapping(DataContext dataContext) {
        super(dataContext, "m_pocket_accounts_mapping");
        this.ID = this.delegate.getColumnByName("id");
        this.POCKET_ID = this.delegate.getColumnByName("pocket_id");
        this.ACCOUNT_ID = this.delegate.getColumnByName("account_id");
        this.ACCOUNT_TYPE = this.delegate.getColumnByName("account_type");
        this.ACCOUNT_NUMBER = this.delegate.getColumnByName("account_number");
    }

}
