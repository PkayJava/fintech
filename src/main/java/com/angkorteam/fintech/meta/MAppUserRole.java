package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.*;

import java.util.Collection;
import java.util.List;

public class MAppUserRole extends AbstractTable {

    public final Column APP_USER_ID;

    public final Column ROLE_ID;

    public static MAppUserRole staticInitialize(DataContext dataContext) {
        return new MAppUserRole(dataContext);
    }

    private MAppUserRole(DataContext dataContext) {
        super(dataContext, "m_appuser_role");
        this.APP_USER_ID = this.delegate.getColumnByName("appuser_id");
        this.ROLE_ID = this.delegate.getColumnByName("role_id");
    }

}
