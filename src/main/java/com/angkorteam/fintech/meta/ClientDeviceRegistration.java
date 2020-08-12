package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class ClientDeviceRegistration extends AbstractTable {

    public final Column ID;

    public final Column CLIENT_ID;

    public final Column UPDATED_ON_DATE;

    public final Column REGISTRATION_ID;

    public static ClientDeviceRegistration staticInitialize(DataContext dataContext) {
        return new ClientDeviceRegistration(dataContext);
    }

    private ClientDeviceRegistration(DataContext dataContext) {
        super(dataContext, "client_device_registration");
        this.ID = this.delegate.getColumnByName("id");
        this.CLIENT_ID = this.delegate.getColumnByName("client_id");
        this.UPDATED_ON_DATE = this.delegate.getColumnByName("updatedon_date");
        this.REGISTRATION_ID = this.delegate.getColumnByName("registration_id");
    }

}
