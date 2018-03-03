package com.angkorteam.fintech.ddl;

public interface MAccountTransferDetails {

    public static final String NAME = "m_account_transfer_details";

    public interface Field {

        public static final String ID = "id";

        public static final String FROM_OFFICE_ID = "from_office_id";

        public static final String TO_OFFICE_ID = "to_office_id";

        public static final String FROM_CLIENT_ID = "from_client_id";

        public static final String TO_CLIENT_ID = "to_client_id";

        public static final String FROM_SAVINGS_ACCOUNT_ID = "from_savings_account_id";

        public static final String TO_SAVINGS_ACCOUNT_ID = "to_savings_account_id";

        public static final String FROM_LOAN_ACCOUNT_ID = "from_loan_account_id";

        public static final String TO_LOAN_ACCOUNT_ID = "to_loan_account_id";

        public static final String TRANSFER_TYPE = "transfer_type";

    }

}
