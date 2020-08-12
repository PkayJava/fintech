package com.angkorteam.fintech.meta;

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
        this.ID = this.delegate.getColumnByName("id");
        this.ENTITY = this.delegate.getColumnByName("entity");
        this.SUB_ENTITY = this.delegate.getColumnByName("subentity");
        this.FIELD = this.delegate.getColumnByName("field");
        this.ENABLED = this.delegate.getColumnByName("is_enabled");
        this.MANDATORY = this.delegate.getColumnByName("is_mandatory");
        this.VALIDATION_REGEX = this.delegate.getColumnByName("validation_regex");
    }

}
