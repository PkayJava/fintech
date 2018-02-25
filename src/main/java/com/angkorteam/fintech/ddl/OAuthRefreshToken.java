package com.angkorteam.fintech.ddl;

public interface OAuthRefreshToken {

    public static final String NAME = "oauth_refresh_token";

    public interface Field {

        public static final String TOKEN_ID = "token_id";

        public static final String TOKEN = "token";

        public static final String AUTHENTICATION = "authentication";
        
    }

}
