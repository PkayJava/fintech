package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MPermission extends AbstractTable {

    public final Column ID;

    public final Column GROUPING;

    public final Column CODE;

    public final Column ENTITY_NAME;

    public final Column ACTION_NAME;

    public final Column CAN_MAKER_CHECKER;

    public static MPermission staticInitialize(DataContext dataContext) {
        return new MPermission(dataContext);
    }

    private MPermission(DataContext dataContext) {
        super(dataContext, "m_permission");
        this.ID = this.delegate.getColumnByName("id");
        this.GROUPING = this.delegate.getColumnByName("grouping");
        this.CODE = this.delegate.getColumnByName("code");
        this.ENTITY_NAME = this.delegate.getColumnByName("entity_name");
        this.ACTION_NAME = this.delegate.getColumnByName("action_name");
        this.CAN_MAKER_CHECKER = this.delegate.getColumnByName("can_maker_checker");
    }

}
