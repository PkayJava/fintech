package com.angkorteam.fintech.ddl;

public interface MPortfolioAccountAssociations {

    public static final String NAME = "m_portfolio_account_associations";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_ACCOUNT_ID = "loan_account_id";

        public static final String SAVINGS_ACCOUNT_ID = "savings_account_id";

        public static final String LINKED_LOAN_ACCOUNT_ID = "linked_loan_account_id";

        public static final String LINKED_SAVINGS_ACCOUNT_ID = "linked_savings_account_id";

        public static final String ASSOCIATION_TYPE_ENUM = "association_type_enum";

        public static final String IS_ACTIVE = "is_active";

    }

}
