package com.angkorteam.fintech.ddl;

public interface TwoFactorAccessToken {

    public static final String NAME = "twofactor_access_token";

    public interface Field {

        public static final String ID = "id";

        public static final String TOKEN = "token";

        public static final String APP_USER_ID = "appuser_id";

        public static final String VALID_FROM = "valid_from";

        public static final String VALID_TO = "valid_to";

        public static final String ENABLED = "enabled";

    }

}
