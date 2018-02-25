package com.angkorteam.fintech.ddl;

public interface MProvisioningCriteriaDefinition {

    public static final String NAME = "m_provisioning_criteria_definition";

    public interface Field {

        public static final String ID = "id";

        public static final String CRITERIA_ID = "criteria_id";

        public static final String CATEGORY_ID = "category_id";

        public static final String MIN_AGE = "min_age";

        public static final String MAX_AGE = "max_age";

        public static final String PROVISION_PERCENTAGE = "provision_percentage";

        public static final String LIABILITY_ACCOUNT = "liability_account";

        public static final String EXPENSE_ACCOUNT = "expense_account";

    }

}
