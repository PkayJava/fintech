package com.angkorteam.fintech.ddl;

public interface MPortfolioCommandSource {

    public static final String NAME = "m_portfolio_command_source";

    public interface Field {

        public static final String ID = "id";

        public static final String ACTION_NAME = "action_name";

        public static final String ENTITY_NAME = "entity_name";

        public static final String OFFICE_ID = "office_id";

        public static final String GROUP_ID = "group_id";

        public static final String CLIENT_ID = "client_id";

        public static final String LOAN_ID = "loan_id";

        public static final String SAVINGS_ACCOUNT_ID = "savings_account_id";

        public static final String API_GET_URL = "api_get_url";

        public static final String RESOURCE_ID = "resource_id";

        public static final String SUB_RESOURCE_ID = "subresource_id";

        public static final String COMMAND_AS_JSON = "command_as_json";

        public static final String MAKER_ID = "maker_id";

        public static final String MADE_ON_DATE = "made_on_date";

        public static final String CHECKER_ID = "checker_id";

        public static final String CHECKED_ON_DATE = "checked_on_date";

        public static final String PROCESSING_RESULT_ENUM = "processing_result_enum";

        public static final String PRODUCT_ID = "product_id";

        public static final String TRANSACTION_ID = "transaction_id";

        public static final String CREDIT_BUREAU_ID = "creditbureau_id";

        public static final String ORGANISATION_CREDIT_BUREAU_ID = "organisation_creditbureau_id";

    }

}
