package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.CLIENT_ID = this.delegate.getColumnByName("client_id");
        this.GROUP_ID = this.delegate.getColumnByName("group_id");
        this.ROLE_CV_ID = this.delegate.getColumnByName("role_cv_id");
    }

}
