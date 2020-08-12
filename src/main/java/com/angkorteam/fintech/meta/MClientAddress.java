package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.*;

import java.util.Collection;
import java.util.List;

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
        this.ID = this.delegate.getColumnByName("id");
        this.CLIENT_ID = this.delegate.getColumnByName("client_id");
        this.ADDRESS_ID = this.delegate.getColumnByName("address_id");
        this.ADDRESS_TYPE_ID = this.delegate.getColumnByName("address_type_id");
        this.ACTIVE = this.delegate.getColumnByName("is_active");
    }

}
