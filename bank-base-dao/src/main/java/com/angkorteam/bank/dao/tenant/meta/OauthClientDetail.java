package com.angkorteam.bank.dao.tenant.meta;

import com.angkorteam.metamodel.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class OauthClientDetail extends AbstractTable {

    public final Column CLIENT_ID;

    public final Column RESOURCE_ID;

    public final Column CLIENT_SECRET;

    public final Column SCOPE;

    public final Column AUTHORIZED_GRANT_TYPE;

    public final Column WEB_SERVER_REDIRECT_URI;

    public final Column AUTHORITY;

    public final Column ACCESS_TOKEN_VALIDITY;

    public final Column REFRESH_TOKEN_VALIDITY;

    public final Column ADDITIONAL_INFORMATION;

    public final Column AUTO_APPROVE;

    public static OauthClientDetail staticInitialize(DataContext dataContext) {
        return new OauthClientDetail(dataContext);
    }

    private OauthClientDetail(DataContext dataContext) {
        super(dataContext, "oauth_client_details");
        this.CLIENT_ID = getInternalColumnByName("client_id");
        this.RESOURCE_ID = getInternalColumnByName("resource_ids");
        this.CLIENT_SECRET = getInternalColumnByName("client_secret");
        this.SCOPE = getInternalColumnByName("scope");
        this.AUTHORIZED_GRANT_TYPE = getInternalColumnByName("authorized_grant_types");
        this.WEB_SERVER_REDIRECT_URI = getInternalColumnByName("web_server_redirect_uri");
        this.AUTHORITY = getInternalColumnByName("authorities");
        this.ACCESS_TOKEN_VALIDITY = getInternalColumnByName("access_token_validity");
        this.REFRESH_TOKEN_VALIDITY = getInternalColumnByName("refresh_token_validity");
        this.ADDITIONAL_INFORMATION = getInternalColumnByName("additional_information");
        this.AUTO_APPROVE = getInternalColumnByName("autoapprove");
    }

}
