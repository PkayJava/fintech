package com.angkorteam.fintech.ddl;

public interface MAppUser {

    public static final String NAME = "m_appuser";

    public interface Field {

        public static final String ID = "id";

        public static final String IS_DELETED = "is_deleted";

        public static final String OFFICE_ID = "office_id";

        public static final String STAFF_ID = "staff_id";

        public static final String USERNAME = "username";

        public static final String FIRST_NAME = "firstname";

        public static final String LAST_NAME = "lastname";

        public static final String PASSWORD = "password";

        public static final String EMAIL = "email";

        public static final String FIRST_TIME_LOGIN_REMAINING = "firsttime_login_remaining";

        public static final String NON_EXPIRED = "nonexpired";

        public static final String NON_LOCKED = "nonlocked";

        public static final String NON_EXPIRED_CREDENTIALS = "nonexpired_credentials";

        public static final String ENABLED = "enabled";

        public static final String LAST_TIME_PASSWORD_UPDATED = "last_time_password_updated";

        public static final String PASSWORD_NEVER_EXPIRES = "password_never_expires";

        public static final String IS_SELF_SERVICE_USER = "is_self_service_user";

    }

}
