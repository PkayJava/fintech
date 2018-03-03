package com.angkorteam.fintech.ddl;

public interface MProductLoanGuaranteeDetails {

    public static final String NAME = "m_product_loan_guarantee_details";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_PRODUCT_ID = "loan_product_id";

        public static final String MANDATORY_GUARANTEE = "mandatory_guarantee";

        public static final String MINIMUM_GUARANTEE_FROM_OWN_FUNDS = "minimum_guarantee_from_own_funds";

        public static final String MINIMUM_GUARANTEE_FROM_GUARANTOR_FUNDS = "minimum_guarantee_from_guarantor_funds";

    }

}
