package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class OauthRefreshToken extends AbstractTable {

    public final Column TOKEN_ID;

    public final Column TOKEN;

    public final Column AUTHENTICATION;

    public static OauthRefreshToken staticInitialize(DataContext dataContext) {
        return new OauthRefreshToken(dataContext);
    }

    private OauthRefreshToken(DataContext dataContext) {
        super(dataContext, "oauth_refresh_token");
        this.TOKEN_ID = getInternalColumnByName("token_id");
        this.TOKEN = getInternalColumnByName("token");
        this.AUTHENTICATION = getInternalColumnByName("authentication");
    }

}
