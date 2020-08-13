package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MRole extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column DESCRIPTION;

    public final Column DISABLED;

    public static MRole staticInitialize(DataContext dataContext) {
        return new MRole(dataContext);
    }

    private MRole(DataContext dataContext) {
        super(dataContext, "m_role");
        this.ID = this.delegate.getColumnByName("id");
        this.NAME = this.delegate.getColumnByName("name");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.DISABLED = this.delegate.getColumnByName("is_disabled");
    }

}
