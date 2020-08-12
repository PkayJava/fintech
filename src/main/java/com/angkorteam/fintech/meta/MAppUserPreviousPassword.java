package com.angkorteam.fintech.meta;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class MAppUserPreviousPassword extends AbstractTable {

    public final Column ID;

    public final Column USER_ID;

    public final Column PASSWORD;

    public final Column REMOVAL_DATE;

    public static MAppUserPreviousPassword staticInitialize(DataContext dataContext) {
        return new MAppUserPreviousPassword(dataContext);
    }

    private MAppUserPreviousPassword(DataContext dataContext) {
        super(dataContext, "m_appuser_previous_password");
        this.ID = this.delegate.getColumnByName("id");
        this.USER_ID = this.delegate.getColumnByName("user_id");
        this.PASSWORD = this.delegate.getColumnByName("password");
        this.REMOVAL_DATE = this.delegate.getColumnByName("removal_date");
    }

}
