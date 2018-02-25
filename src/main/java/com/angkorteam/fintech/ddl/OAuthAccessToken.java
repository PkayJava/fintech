package com.angkorteam.fintech.ddl;

public interface OAuthAccessToken {

    public static final String NAME = "oauth_access_token";

    public interface Field {

        public static final String TOKEN_ID = "token_id";

        public static final String TOKEN = "token";

        public static final String AUTHENTICATION_ID = "authentication_id";

        public static final String USER_NAME = "user_name";

        public static final String CLIENT_ID = "client_id";

        public static final String AUTHENTICATION = "authentication";

        public static final String REFRESH_TOKEN = "refresh_token";
    }

}
