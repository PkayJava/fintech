package com.angkorteam.fintech.ddl;

public interface MCreditBureauLoanProductMapping {

    public static final String NAME = "m_creditbureau_loanproduct_mapping";

    public interface Field {

        public static final String ID = "id";

        public static final String ORGANISATION_CREDIT_BUREAU_ID = "organisation_creditbureau_id";

        public static final String LOAN_PRODUCT_ID = "loan_product_id";

        public static final String IS_CREDIT_CHECK_MANDATORY = "is_creditcheck_mandatory";

        public static final String SKIP_CREDIT_CHECK_IN_FAILURE = "skip_creditcheck_in_failure";

        public static final String STALE_PERIOD = "stale_period";

        public static final String IS_ACTIVE = "is_active";

    }

}
