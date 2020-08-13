package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.PARENT_ID = this.delegate.getColumnByName("parent_id");
        this.SUPER_PARENT = this.delegate.getColumnByName("super_parent");
        this.LEVEL_NAME = this.delegate.getColumnByName("level_name");
        this.RECURSABLE = this.delegate.getColumnByName("recursable");
        this.CAN_HAVE_CLIENT = this.delegate.getColumnByName("can_have_clients");
    }

}
