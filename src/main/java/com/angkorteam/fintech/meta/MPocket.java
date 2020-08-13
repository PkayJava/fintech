package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.APP_USER_ID = this.delegate.getColumnByName("app_user_id");
    }

}
