package com.angkorteam.fintech.ddl;

public interface MNote {

    public static final String NAME = "m_note";

    public interface Field {

        public static final String ID = "id";

        public static final String CLIENT_ID = "client_id";

        public static final String GROUP_ID = "group_id";

        public static final String LOAN_ID = "loan_id";

        public static final String LOAN_TRANSACTION_ID = "loan_transaction_id";

        public static final String SAVINGS_ACCOUNT_ID = "savings_account_id";

        public static final String SAVINGS_ACCOUNT_TRANSACTION_ID = "savings_account_transaction_id";

        public static final String SHARE_ACCOUNT_ID = "share_account_id";

        public static final String NOTE_TYPE_ENUM = "note_type_enum";

        public static final String NOTE = "note";

        public static final String CREATED_DATE = "created_date";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

    }

}
