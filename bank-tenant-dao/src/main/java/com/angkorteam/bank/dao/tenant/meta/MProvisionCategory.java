package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MProvisionCategory extends AbstractTable {

    public final Column ID;

    public final Column CATEGORY_NAME;

    public final Column DESCRIPTION;

    public static MProvisionCategory staticInitialize(DataContext dataContext) {
        return new MProvisionCategory(dataContext);
    }

    private MProvisionCategory(DataContext dataContext) {
        super(dataContext, "m_provision_category");
        this.ID = getInternalColumnByName("id");
        this.CATEGORY_NAME = getInternalColumnByName("category_name");
        this.DESCRIPTION = getInternalColumnByName("description");
    }

}
