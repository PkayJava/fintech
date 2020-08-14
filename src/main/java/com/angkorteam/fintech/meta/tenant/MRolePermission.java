package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MRolePermission extends AbstractTable {

    public final Column ROLE_ID;

    public final Column PERMISSION_ID;

    public static MRolePermission staticInitialize(DataContext dataContext) {
        return new MRolePermission(dataContext);
    }

    private MRolePermission(DataContext dataContext) {
        super(dataContext, "m_role_permission");
        this.ROLE_ID = getInternalColumnByName("role_id");
        this.PERMISSION_ID = getInternalColumnByName("permission_id");
    }

}
