package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.*;

public class MClientAddress extends AbstractTable {

    public final Column ID;

    public final Column CLIENT_ID;

    public final Column ADDRESS_ID;

    public final Column ADDRESS_TYPE_ID;

    public final Column ACTIVE;

    public static MClientAddress staticInitialize(DataContext dataContext) {
        return new MClientAddress(dataContext);
    }

    private MClientAddress(DataContext dataContext) {
        super(dataContext, "m_client_address");
        this.ID = getInternalColumnByName("id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.ADDRESS_ID = getInternalColumnByName("address_id");
        this.ADDRESS_TYPE_ID = getInternalColumnByName("address_type_id");
        this.ACTIVE = getInternalColumnByName("is_active");
    }

}
