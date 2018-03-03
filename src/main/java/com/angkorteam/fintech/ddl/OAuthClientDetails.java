package com.angkorteam.fintech.ddl;

public interface OAuthClientDetails {

    public static final String NAME = "oauth_client_details";

    public interface Field {

        public static final String CLIENT_ID = "client_id";

        public static final String RESOURCE_IDS = "resource_ids";

        public static final String CLIENT_SECRET = "client_secret";

        public static final String SCOPE = "scope";

        public static final String AUTHORIZED_GRANT_TYPES = "authorized_grant_types";

        public static final String WEB_SERVER_REDIRECT_URI = "web_server_redirect_uri";

        public static final String AUTHORITIES = "authorities";

        public static final String ACCESS_TOKEN_VALIDITY = "access_token_validity";

        public static final String REFRESH_TOKEN_VALIDITY = "refresh_token_validity";

        public static final String ADDITIONAL_INFORMATION = "additional_information";

        public static final String AUTO_APPROVE = "autoapprove";
    }

}
