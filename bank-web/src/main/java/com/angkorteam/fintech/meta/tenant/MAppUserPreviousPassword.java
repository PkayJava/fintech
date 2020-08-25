package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
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
        this.ID = getInternalColumnByName("id");
        this.USER_ID = getInternalColumnByName("user_id");
        this.PASSWORD = getInternalColumnByName("password");
        this.REMOVAL_DATE = getInternalColumnByName("removal_date");
    }

}
