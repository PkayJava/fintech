package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MFieldConfiguration extends AbstractTable {

    public final Column ID;

    public final Column ENTITY;

    public final Column SUB_ENTITY;

    public final Column FIELD;

    public final Column ENABLED;

    public final Column MANDATORY;

    public final Column VALIDATION_REGEX;

    public static MFieldConfiguration staticInitialize(DataContext dataContext) {
        return new MFieldConfiguration(dataContext);
    }

    private MFieldConfiguration(DataContext dataContext) {
        super(dataContext, "m_field_configuration");
        this.ID = getInternalColumnByName("id");
        this.ENTITY = getInternalColumnByName("entity");
        this.SUB_ENTITY = getInternalColumnByName("subentity");
        this.FIELD = getInternalColumnByName("field");
        this.ENABLED = getInternalColumnByName("is_enabled");
        this.MANDATORY = getInternalColumnByName("is_mandatory");
        this.VALIDATION_REGEX = getInternalColumnByName("validation_regex");
    }

}
