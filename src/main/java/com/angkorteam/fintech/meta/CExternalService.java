package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class CExternalService extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public static CExternalService staticInitialize(DataContext dataContext) {
        return new CExternalService(dataContext);
    }

    private CExternalService(DataContext dataContext) {
        super(dataContext, "c_external_service");
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
    }

}
