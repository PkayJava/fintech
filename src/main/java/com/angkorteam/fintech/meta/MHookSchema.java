package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MHookSchema extends AbstractTable {

    public final Column ID;

    public final Column HOOK_TEMPLATE_ID;

    public final Column FIELD_TYPE;

    public final Column FIELD_NAME;

    public final Column PLACEHOLDER;

    public final Column OPTIONAL;

    public static MHookSchema staticInitialize(DataContext dataContext) {
        return new MHookSchema(dataContext);
    }

    private MHookSchema(DataContext dataContext) {
        super(dataContext, "m_hook_schema");
        this.ID = this.delegate.getColumnByName("id");
        this.HOOK_TEMPLATE_ID = this.delegate.getColumnByName("hook_template_id");
        this.FIELD_TYPE = this.delegate.getColumnByName("field_type");
        this.FIELD_NAME = this.delegate.getColumnByName("field_name");
        this.PLACEHOLDER = this.delegate.getColumnByName("placeholder");
        this.OPTIONAL = this.delegate.getColumnByName("optional");
    }

}
