package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MPocket extends AbstractTable {

    public final Column ID;

    public final Column APP_USER_ID;

    public static MPocket staticInitialize(DataContext dataContext) {
        return new MPocket(dataContext);
    }

    private MPocket(DataContext dataContext) {
        super(dataContext, "m_pocket");
        this.ID = getInternalColumnByName("id");
        this.APP_USER_ID = getInternalColumnByName("app_user_id");
    }

}
