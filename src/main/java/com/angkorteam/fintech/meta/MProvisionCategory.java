package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.CATEGORY_NAME = this.delegate.getColumnByName("category_name");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
    }

}
