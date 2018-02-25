package com.angkorteam.fintech.ddl;

public interface MProductLoanVariationsBorrowerCycle {

    public static final String NAME = "m_product_loan_variations_borrower_cycle";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_PRODUCT_ID = "loan_product_id";

        public static final String BORROWER_CYCLE_NUMBER = "borrower_cycle_number";

        public static final String VALUE_CONDITION = "value_condition";

        public static final String PARAM_TYPE = "param_type";

        public static final String DEFAULT_VALUE = "default_value";

        public static final String MAX_VALUE = "max_value";

        public static final String MIN_VALUE = "min_value";

    }

}
