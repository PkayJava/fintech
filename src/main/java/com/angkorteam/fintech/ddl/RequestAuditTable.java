package com.angkorteam.fintech.ddl;

public interface RequestAuditTable {

    public static final String NAME = "request_audit_table";

    public interface Field {

        public static final String ID = "id";

        public static final String LAST_NAME = "lastname";

        public static final String USERNAME = "username";

        public static final String MOBILE_NUMBER = "mobile_number";

        public static final String FIRST_NAME = "firstname";

        public static final String AUTHENTICATION_TOKEN = "authentication_token";

        public static final String PASSWORD = "password";

        public static final String EMAIL = "email";

        public static final String CLIENT_ID = "client_id";

        public static final String CREATED_DATE = "created_date";

        public static final String ACCOUNT_NUMBER = "account_number";

    }

}
