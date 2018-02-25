package com.angkorteam.fintech.ddl;

public interface MTemplate {

    public static final String NAME = "m_tellers";

    public interface Field {

        public static final String ID = "id";

        public static final String OFFICE_ID = "office_id";

        public static final String DEBIT_ACCOUNT_ID = "debit_account_id";

        public static final String CREDIT_ACCOUNT_ID = "credit_account_id";

        public static final String NAME = "name";

        public static final String DESCRIPTION = "description";

        public static final String VALID_FROM = "valid_from";

        public static final String VALID_TO = "valid_to";

        public static final String STATE = "state";

    }

}
