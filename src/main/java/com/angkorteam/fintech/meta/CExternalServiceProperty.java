package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class CExternalServiceProperty extends AbstractTable {

    public final Column NAME;

    public final Column VALUE;

    public final Column EXTERNAL_SERVICE_ID;

    public static CExternalServiceProperty staticInitialize(DataContext dataContext) {
        return new CExternalServiceProperty(dataContext);
    }

    private CExternalServiceProperty(DataContext dataContext) {
        super(dataContext, "c_external_service_properties");
        this.NAME = this.delegate.getColumnByName("name");
        this.VALUE = this.delegate.getColumnByName("value");
        this.EXTERNAL_SERVICE_ID = this.delegate.getColumnByName("external_service_id");
    }

}
