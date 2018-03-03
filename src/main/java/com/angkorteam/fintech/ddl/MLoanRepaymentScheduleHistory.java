package com.angkorteam.fintech.ddl;

public interface MLoanRepaymentScheduleHistory {

    public static final String NAME = "m_loan_repayment_schedule_history";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_ID = "loan_id";

        public static final String LOAN_RESCHEDULE_REQUEST_ID = "loan_reschedule_request_id";

        public static final String FROM_DATE = "fromdate";

        public static final String DUE_DATE = "duedate";

        public static final String INSTALLMENT = "installment";

        public static final String PRINCIPAL_AMOUNT = "principal_amount";

        public static final String INTEREST_AMOUNT = "interest_amount";

        public static final String FEE_CHARGES_AMOUNT = "fee_charges_amount";

        public static final String PENALTY_CHARGES_AMOUNT = "penalty_charges_amount";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String CREATED_DATE = "created_date";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

        public static final String VERSION = "version";

    }

}
