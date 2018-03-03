package com.angkorteam.fintech.ddl;

public interface MProductLoanConfigurableAttributes {

    public static final String NAME = "m_product_loan_configurable_attributes";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_PRODUCT_ID = "loan_product_id";

        public static final String AMORTIZATION_METHOD_ENUM = "amortization_method_enum";

        public static final String INTEREST_METHOD_ENUM = "interest_method_enum";

        public static final String LOAN_TRANSACTION_STRATEGY_ID = "loan_transaction_strategy_id";

        public static final String INTEREST_CALCULATED_IN_PERIOD_ENUM = "interest_calculated_in_period_enum";

        public static final String ARREARS_TOLERANCE_AMOUNT = "arrearstolerance_amount";

        public static final String REPAY_EVERY = "repay_every";

        public static final String MORATORIUM = "moratorium";

        public static final String GRACE_ON_ARREARS_AGEING = "grace_on_arrears_ageing";

    }

}
