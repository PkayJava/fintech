package com.angkorteam.fintech.ddl;

public interface MLoanTopup {

    public static final String NAME = "m_loan_topup";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_ID = "loan_id";

        public static final String CLOSURE_LOAN_ID = "closure_loan_id";

        public static final String ACCOUNT_TRANSFER_DETAILS_ID = "account_transfer_details_id";

        public static final String TOPUP_AMOUNT = "topup_amount";

    }

}
