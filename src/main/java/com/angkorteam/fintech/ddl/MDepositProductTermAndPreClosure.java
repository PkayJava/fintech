package com.angkorteam.fintech.ddl;

public interface MDepositProductTermAndPreClosure {

    public static final String NAME = "m_deposit_product_term_and_preclosure";

    public interface Field {

        public static final String ID = "id";

        public static final String SAVINGS_PRODUCT_ID = "savings_product_id";

        public static final String MIN_DEPOSIT_TERM = "min_deposit_term";

        public static final String MAX_DEPOSIT_TERM = "max_deposit_term";

        public static final String MIN_DEPOSIT_TERM_TYPE_ENUM = "min_deposit_term_type_enum";

        public static final String MAX_DEPOSIT_TERM_TYPE_ENUM = "max_deposit_term_type_enum";

        public static final String IN_MULTIPLES_OF_DEPOSIT_TERM = "in_multiples_of_deposit_term";

        public static final String IN_MULTIPLES_OF_DEPOSIT_TERM_TYPE_ENUM = "in_multiples_of_deposit_term_type_enum";

        public static final String PRE_CLOSURE_PENAL_APPLICABLE = "pre_closure_penal_applicable";

        public static final String PRE_CLOSURE_PENAL_INTEREST = "pre_closure_penal_interest";

        public static final String PRE_CLOSURE_PENAL_INTEREST_ON_ENUM = "pre_closure_penal_interest_on_enum";

        public static final String MIN_DEPOSIT_AMOUNT = "min_deposit_amount";

        public static final String MAX_DEPOSIT_AMOUNT = "max_deposit_amount";

        public static final String DEPOSIT_AMOUNT = "deposit_amount";

    }

}
