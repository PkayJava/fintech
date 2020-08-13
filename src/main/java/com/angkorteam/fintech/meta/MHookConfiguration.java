package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.HOOK_ID = this.delegate.getColumnByName("hook_id");
        this.FIELD_TYPE = this.delegate.getColumnByName("field_type");
        this.FIELD_NAME = this.delegate.getColumnByName("field_name");
        this.FIELD_VALUE = this.delegate.getColumnByName("field_value");
    }

}
