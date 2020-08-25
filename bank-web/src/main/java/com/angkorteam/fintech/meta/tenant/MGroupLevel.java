package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MGroupLevel extends AbstractTable {

    public final Column ID;

    public final Column PARENT_ID;

    public final Column SUPER_PARENT;

    public final Column LEVEL_NAME;

    public final Column RECURSABLE;

    public final Column CAN_HAVE_CLIENT;

    public static MGroupLevel staticInitialize(DataContext dataContext) {
        return new MGroupLevel(dataContext);
    }

    private MGroupLevel(DataContext dataContext) {
        super(dataContext, "m_group_level");
        this.ID = getInternalColumnByName("id");
        this.PARENT_ID = getInternalColumnByName("parent_id");
        this.SUPER_PARENT = getInternalColumnByName("super_parent");
        this.LEVEL_NAME = getInternalColumnByName("level_name");
        this.RECURSABLE = getInternalColumnByName("recursable");
        this.CAN_HAVE_CLIENT = getInternalColumnByName("can_have_clients");
    }

}
