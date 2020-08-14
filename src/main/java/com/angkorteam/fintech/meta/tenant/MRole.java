package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.DISABLED = getInternalColumnByName("is_disabled");
    }

}
