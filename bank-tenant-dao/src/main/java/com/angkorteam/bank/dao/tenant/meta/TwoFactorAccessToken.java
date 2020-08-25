package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.bank.dao.base.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class TwoFactorAccessToken extends AbstractTable {

    public final Column ID;

    public final Column TOKEN;

    public final Column APP_USER_ID;

    public final Column VALID_FROM;

    public final Column VALID_TO;

    public final Column ENABLED;

    public static TwoFactorAccessToken staticInitialize(DataContext dataContext) {
        return new TwoFactorAccessToken(dataContext);
    }

    private TwoFactorAccessToken(DataContext dataContext) {
        super(dataContext, "twofactor_access_token");
        this.ID = getInternalColumnByName("id");
        this.TOKEN = getInternalColumnByName("token");
        this.APP_USER_ID = getInternalColumnByName("appuser_id");
        this.VALID_FROM = getInternalColumnByName("valid_from");
        this.VALID_TO = getInternalColumnByName("valid_to");
        this.ENABLED = getInternalColumnByName("enabled");
    }

}
