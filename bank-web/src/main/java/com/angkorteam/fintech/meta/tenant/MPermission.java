package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.GROUPING = getInternalColumnByName("grouping");
        this.CODE = getInternalColumnByName("code");
        this.ENTITY_NAME = getInternalColumnByName("entity_name");
        this.ACTION_NAME = getInternalColumnByName("action_name");
        this.CAN_MAKER_CHECKER = getInternalColumnByName("can_maker_checker");
    }

}
