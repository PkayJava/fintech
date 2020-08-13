package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MPasswordValidationPolicy extends AbstractTable {

    public final Column ID;

    public final Column REGEX;

    public final Column DESCRIPTION;

    public final Column ACTIVE;

    public final Column KEY;

    public static MPasswordValidationPolicy staticInitialize(DataContext dataContext) {
        return new MPasswordValidationPolicy(dataContext);
    }

    private MPasswordValidationPolicy(DataContext dataContext) {
        super(dataContext, "m_password_validation_policy");
        this.ID = this.delegate.getColumnByName("id");
        this.REGEX = this.delegate.getColumnByName("regex");
        this.DESCRIPTION = this.delegate.getColumnByName("description");
        this.ACTIVE = this.delegate.getColumnByName("active");
        this.KEY = this.delegate.getColumnByName("key");
    }

}
