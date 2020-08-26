package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class OauthAccessToken extends AbstractTable {

    public final Column TOKEN_ID;

    public final Column TOKEN;

    public final Column AUTHENTICATION_ID;

    public final Column USER_NAME;

    public final Column CLIENT_ID;

    public final Column AUTHENTICATION;

    public final Column REFRESH_TOKEN;

    public static OauthAccessToken staticInitialize(DataContext dataContext) {
        return new OauthAccessToken(dataContext);
    }

    private OauthAccessToken(DataContext dataContext) {
        super(dataContext, "oauth_access_token");
        this.TOKEN_ID = getInternalColumnByName("token_id");
        this.TOKEN = getInternalColumnByName("token");
        this.AUTHENTICATION_ID = getInternalColumnByName("authentication_id");
        this.USER_NAME = getInternalColumnByName("user_name");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.AUTHENTICATION = getInternalColumnByName("authentication");
        this.REFRESH_TOKEN = getInternalColumnByName("refresh_token");
    }

}
