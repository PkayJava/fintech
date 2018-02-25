package com.angkorteam.fintech.ddl;

public interface AccGLJournalEntry {

    public static final String NAME = "acc_gl_journal_entry";

    public interface Field {

        public static final String ID = "id";

        public static final String ACCOUNT_ID = "account_id";

        public static final String CURRENCY_CODE = "currency_code";

        public static final String TRANSACTION_ID = "transaction_id";

        public static final String LOAN_TRANSACTION_ID = "loan_transaction_id";

        public static final String SAVINGS_TRANSACTION_ID = "savings_transaction_id";

        public static final String CLIENT_TRANSACTION_ID = "client_transaction_id";

        public static final String REVERSED = "reversed";

        public static final String REF_NUM = "ref_num";

        public static final String MANUAL_ENTRY = "manual_entry";

        public static final String ENTRY_DATE = "entry_date";

        public static final String TYPE_ENUM = "type_enum";

        public static final String AMOUNT = "amount";

        public static final String DESCRIPTION = "description";

        public static final String ENTITY_TYPE_ENUM = "entity_type_enum";

        public static final String ENTITY_ID = "entity_id";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

        public static final String CREATED_DATE = "created_date";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

        public static final String IS_RUNNING_BALANCE_CALCULATED = "is_running_balance_calculated";

        public static final String OFFICE_RUNNING_BALANCE = "office_running_balance";

        public static final String ORGANIZATION_RUNNING_BALANCE = "organization_running_balance";

        public static final String PAYMENT_DETAILS_ID = "payment_details_id";

        public static final String SHARE_TRANSACTION_ID = "share_transaction_id";

    }

}
