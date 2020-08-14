package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.*;

public class MAppUserRole extends AbstractTable {

    public final Column APP_USER_ID;

    public final Column ROLE_ID;

    public static MAppUserRole staticInitialize(DataContext dataContext) {
        return new MAppUserRole(dataContext);
    }

    private MAppUserRole(DataContext dataContext) {
        super(dataContext, "m_appuser_role");
        this.APP_USER_ID = getInternalColumnByName("appuser_id");
        this.ROLE_ID = getInternalColumnByName("role_id");
    }

}
