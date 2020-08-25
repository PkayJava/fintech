package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
    }

}
