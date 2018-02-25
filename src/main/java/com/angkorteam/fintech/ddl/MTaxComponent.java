package com.angkorteam.fintech.ddl;

public interface MTaxComponent {

    public static final String NAME = "m_tax_component";

    public interface Field {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String PERCENTAGE = "percentage";

        public static final String DEBIT_ACCOUNT_TYPE_ENUM = "debit_account_type_enum";

        public static final String DEBIT_ACCOUNT_ID = "debit_account_id";

        public static final String CREDIT_ACCOUNT_TYPE_ENUM = "credit_account_type_enum";

        public static final String CREDIT_ACCOUNT_ID = "credit_account_id";

        public static final String START_DATE = "start_date";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String CREATED_DATE = "created_date";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

    }

}
