package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MGroupClient extends AbstractTable {

    public final Column GROUP_ID;

    public final Column CLIENT_ID;

    public static MGroupClient staticInitialize(DataContext dataContext) {
        return new MGroupClient(dataContext);
    }

    private MGroupClient(DataContext dataContext) {
        super(dataContext, "m_group_client");
        this.GROUP_ID = getInternalColumnByName("group_id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
    }

}
