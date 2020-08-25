package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MSelfServiceUserClientMapping extends AbstractTable {

    public final Column ID;

    public final Column APP_USER_ID;

    public final Column CLIENT_ID;

    public static MSelfServiceUserClientMapping staticInitialize(DataContext dataContext) {
        return new MSelfServiceUserClientMapping(dataContext);
    }

    private MSelfServiceUserClientMapping(DataContext dataContext) {
        super(dataContext, "m_selfservice_user_client_mapping");
        this.ID = getInternalColumnByName("id");
        this.APP_USER_ID = getInternalColumnByName("appuser_id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
    }

}
