package com.angkorteam.fintech.ddl;

public interface MDepositAccountTermAndPreClosure {

    public static final String NAME = "m_deposit_account_term_and_preclosure";

    public interface Field {

        public static final String ID = "id";

        public static final String SAVINGS_ACCOUNT_ID = "savings_account_id";

        public static final String MIN_DEPOSIT_TERM = "min_deposit_term";

        public static final String MAX_DEPOSIT_TERM = "max_deposit_term";

        public static final String MIN_DEPOSIT_TERM_TYPE_ENUM = "min_deposit_term_type_enum";

        public static final String MAX_DEPOSIT_TERM_TYPE_ENUM = "max_deposit_term_type_enum";

        public static final String IN_MULTIPLES_OF_DEPOSIT_TERM = "in_multiples_of_deposit_term";

        public static final String IN_MULTIPLES_OF_DEPOSIT_TERM_TYPE_ENUM = "in_multiples_of_deposit_term_type_enum";

        public static final String PRE_CLOSURE_PENAL_APPLICABLE = "pre_closure_penal_applicable";

        public static final String PRE_CLOSURE_PENAL_INTEREST = "pre_closure_penal_interest";

        public static final String PRE_CLOSURE_PENAL_INTEREST_ON_ENUM = "pre_closure_penal_interest_on_enum";

        public static final String DEPOSIT_PERIOD = "deposit_period";

        public static final String DEPOSIT_PERIOD_FREQUENCY_ENUM = "deposit_period_frequency_enum";

        public static final String DEPOSIT_AMOUNT = "deposit_amount";

        public static final String MATURITY_AMOUNT = "maturity_amount";

        public static final String MATURITY_DATE = "maturity_date";

        public static final String ON_ACCOUNT_CLOSURE_ENUM = "on_account_closure_enum";

        public static final String EXPECTED_FIRST_DEPOSITON_DATE = "expected_firstdepositon_date";

        public static final String TRANSFER_INTEREST_TO_LINKED_ACCOUNT = "transfer_interest_to_linked_account";

    }

}
