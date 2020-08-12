package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
        this.VALUE = this.delegate.getColumnByName("value");
        this.DATE_VALUE = this.delegate.getColumnByName("date_value");
        this.ENABLED = this.delegate.getColumnByName("enabled");
        this.TRAP_DOOR = this.delegate.getColumnByName("is_trap_door");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
    }

}
