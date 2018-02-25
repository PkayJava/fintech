package com.angkorteam.fintech.ddl;

public interface MGuarantor {

    public static final String NAME = "m_guarantor";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_ID = "loan_id";

        public static final String CLIENT_RELN_CV_ID = "client_reln_cv_id";

        public static final String TYPE_ENUM = "type_enum";

        public static final String ENTITY_ID = "entity_id";

        public static final String FIRST_NAME = "firstname";

        public static final String LAST_NAME = "lastname";

        public static final String DOB = "dob";

        public static final String ADDRESS_LINE_1 = "address_line_1";

        public static final String ADDRESS_LINE_2 = "address_line_2";

        public static final String CITY = "city";

        public static final String STATE = "state";

        public static final String COUNTRY = "country";

        public static final String ZIP = "zip";

        public static final String HOUSE_PHONE_NUMBER = "house_phone_number";

        public static final String MOBILE_NUMBER = "mobile_number";

        public static final String COMMENT = "comment";

        public static final String IS_ACTIVE = "is_active";

    }

}
