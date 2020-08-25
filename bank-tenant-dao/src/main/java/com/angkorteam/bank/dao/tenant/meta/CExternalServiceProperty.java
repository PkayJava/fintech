package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
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
        this.NAME = getInternalColumnByName("name");
        this.VALUE = getInternalColumnByName("value");
        this.EXTERNAL_SERVICE_ID = getInternalColumnByName("external_service_id");
    }

}
