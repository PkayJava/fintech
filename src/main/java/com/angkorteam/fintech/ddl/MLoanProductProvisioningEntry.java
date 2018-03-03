package com.angkorteam.fintech.ddl;

public interface MLoanProductProvisioningEntry {

    public static final String NAME = "m_loanproduct_provisioning_entry";

    public interface Field {

        public static final String ID = "id";

        public static final String HISTORY_ID = "history_id";

        public static final String CRITERIA_ID = "criteria_id";

        public static final String CURRENCY_CODE = "currency_code";

        public static final String OFFICE_ID = "office_id";

        public static final String PRODUCT_ID = "product_id";

        public static final String CATEGORY_ID = "category_id";

        public static final String OVER_DUE_IN_DAYS = "overdue_in_days";

        public static final String RESEVE_AMOUNT = "reseve_amount";

        public static final String LIABILITY_ACCOUNT = "liability_account";

        public static final String EXPENSE_ACCOUNT = "expense_account";

    }

}
