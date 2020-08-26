package com.angkorteam.bank.dao.base.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MGroupRole extends AbstractTable {

    public final Column ID;

    public final Column CLIENT_ID;

    public final Column GROUP_ID;

    public final Column ROLE_CV_ID;

    public static MGroupRole staticInitialize(DataContext dataContext) {
        return new MGroupRole(dataContext);
    }

    private MGroupRole(DataContext dataContext) {
        super(dataContext, "m_group_roles");
        this.ID = getInternalColumnByName("id");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.GROUP_ID = getInternalColumnByName("group_id");
        this.ROLE_CV_ID = getInternalColumnByName("role_cv_id");
    }

}
