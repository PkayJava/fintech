package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MHookTemplate extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public static MHookTemplate staticInitialize(DataContext dataContext) {
        return new MHookTemplate(dataContext);
    }

    private MHookTemplate(DataContext dataContext) {
        super(dataContext, "m_hook_templates");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
    }

}
