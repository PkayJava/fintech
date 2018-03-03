package com.angkorteam.fintech.ddl;

public interface MGroup {

    public static final String NAME = "m_group";

    public interface Field {

        public static final String ID = "id";

        public static final String EXTERNAL_ID = "external_id";

        public static final String STATUS_ENUM = "status_enum";

        public static final String ACTIVATION_DATE = "activation_date";

        public static final String OFFICE_ID = "office_id";

        public static final String STAFF_ID = "staff_id";

        public static final String PARENT_ID = "parent_id";

        public static final String LEVEL_ID = "level_id";

        public static final String DISPLAY_NAME = "display_name";

        public static final String HIERARCHY = "hierarchy";

        public static final String CLOSURE_REASON_CV_ID = "closure_reason_cv_id";

        public static final String CLOSED_ON_DATE = "closedon_date";

        public static final String ACTIVATED_ON_USER_ID = "activatedon_userid";

        public static final String SUBMITTED_ON_DATE = "submittedon_date";

        public static final String SUBMITTED_ON_USER_ID = "submittedon_userid";

        public static final String CLOSED_ON_USER_ID = "closedon_userid";

        public static final String ACCOUNT_NO = "account_no";

    }

}
