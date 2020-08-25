package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.REGEX = getInternalColumnByName("regex");
        this.DESCRIPTION = getInternalColumnByName("description");
        this.ACTIVE = getInternalColumnByName("active");
        this.KEY = getInternalColumnByName("key");
    }

}
