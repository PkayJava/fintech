package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class CConfiguration extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column VALUE;

    public final Column DATE_VALUE;

    public final Column ENABLED;

    public final Column TRAP_DOOR;

    public final Column DESCRIPTION;

    public static CConfiguration staticInitialize(DataContext dataContext) {
        return new CConfiguration(dataContext);
    }

    private CConfiguration(DataContext dataContext) {
        super(dataContext, "c_configuration");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.VALUE = getInternalColumnByName("value");
        this.DATE_VALUE = getInternalColumnByName("date_value");
        this.ENABLED = getInternalColumnByName("enabled");
        this.TRAP_DOOR = getInternalColumnByName("is_trap_door");
        this.DESCRIPTION = getInternalColumnByName("description");
    }

}
