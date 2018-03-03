package com.angkorteam.fintech.ddl;

public interface MAdhoc {

    public static final String NAME = "m_adhoc";

    public interface Field {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String QUERY = "query";

        public static final String TABLE_NAME = "table_name";

        public static final String TABLE_FIELDS = "table_fields";

        public static final String EMAIL = "email";

        public static final String IS_ACTIVE = "IsActive";

        public static final String CREATED_DATE = "created_date";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

        public static final String REPORT_RUN_FREQUENCY_CODE = "report_run_frequency_code";

        public static final String REPORT_RUN_EVERY = "report_run_every";

        public static final String LAST_RUN = "last_run";

    }

}
