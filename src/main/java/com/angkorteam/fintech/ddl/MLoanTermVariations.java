package com.angkorteam.fintech.ddl;

public interface MLoanTermVariations {

    public static final String NAME = "m_loan_term_variations";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_ID = "loan_id";

        public static final String TERM_TYPE = "term_type";

        public static final String APPLICABLE_DATE = "applicable_date";

        public static final String DECIMAL_VALUE = "decimal_value";

        public static final String DATE_VALUE = "date_value";

        public static final String IS_SPECIFIC_TO_INSTALLMENT = "is_specific_to_installment";

        public static final String APPLIED_ON_LOAN_STATUS = "applied_on_loan_status";

        public static final String IS_ACTIVE = "is_active";

        public static final String PARENT_ID = "parent_id";

    }

}
