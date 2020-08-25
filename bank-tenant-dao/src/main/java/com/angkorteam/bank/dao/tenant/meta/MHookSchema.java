package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.HOOK_TEMPLATE_ID = getInternalColumnByName("hook_template_id");
        this.FIELD_TYPE = getInternalColumnByName("field_type");
        this.FIELD_NAME = getInternalColumnByName("field_name");
        this.PLACEHOLDER = getInternalColumnByName("placeholder");
        this.OPTIONAL = getInternalColumnByName("optional");
    }

}
