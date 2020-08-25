package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MHookConfiguration extends AbstractTable {

    public final Column ID;

    public final Column HOOK_ID;

    public final Column FIELD_TYPE;

    public final Column FIELD_NAME;

    public final Column FIELD_VALUE;

    public static MHookConfiguration staticInitialize(DataContext dataContext) {
        return new MHookConfiguration(dataContext);
    }

    private MHookConfiguration(DataContext dataContext) {
        super(dataContext, "m_hook_configuration");
        this.ID = getInternalColumnByName("id");
        this.HOOK_ID = getInternalColumnByName("hook_id");
        this.FIELD_TYPE = getInternalColumnByName("field_type");
        this.FIELD_NAME = getInternalColumnByName("field_name");
        this.FIELD_VALUE = getInternalColumnByName("field_value");
    }

}
