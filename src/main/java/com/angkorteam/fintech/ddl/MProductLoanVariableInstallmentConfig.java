package com.angkorteam.fintech.ddl;

public interface MProductLoanVariableInstallmentConfig {

    public static final String NAME = "m_product_loan_variable_installment_config";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_PRODUCT_ID = "loan_product_id";

        public static final String MINIMUM_GAP = "minimum_gap";

        public static final String MAXIMUM_GAP = "maximum_gap";

    }

}
