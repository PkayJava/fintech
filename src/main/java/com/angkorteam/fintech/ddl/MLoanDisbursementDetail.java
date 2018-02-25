package com.angkorteam.fintech.ddl;

public interface MLoanDisbursementDetail {

    public static final String NAME = "m_loan_disbursement_detail";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_ID = "loan_id";

        public static final String EXPECTED_DISBURSE_DATE = "expected_disburse_date";

        public static final String DISBURSED_ON_DATE = "disbursedon_date";

        public static final String PRINCIPAL = "principal";

    }

}
