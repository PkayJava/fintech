package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class TwoFactorConfiguration extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column VALUE;

    public static TwoFactorConfiguration staticInitialize(DataContext dataContext) {
        return new TwoFactorConfiguration(dataContext);
    }

    private TwoFactorConfiguration(DataContext dataContext) {
        super(dataContext, "twofactor_configuration");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.VALUE = getInternalColumnByName("value");
    }

}
