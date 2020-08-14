package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.POCKET_ID = getInternalColumnByName("pocket_id");
        this.ACCOUNT_ID = getInternalColumnByName("account_id");
        this.ACCOUNT_TYPE = getInternalColumnByName("account_type");
        this.ACCOUNT_NUMBER = getInternalColumnByName("account_number");
    }

}
