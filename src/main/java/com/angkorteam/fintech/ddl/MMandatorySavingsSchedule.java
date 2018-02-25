package com.angkorteam.fintech.ddl;

public interface MMandatorySavingsSchedule {

    public static final String NAME = "m_mandatory_savings_schedule";

    public interface Field {

        public static final String ID = "id";

        public static final String SAVINGS_ACCOUNT_ID = "savings_account_id";

        public static final String FROM_DATE = "fromdate";

        public static final String DUE_DATE = "duedate";

        public static final String INSTALLMENT = "installment";

        public static final String DEPOSIT_AMOUNT = "deposit_amount";

        public static final String DEPOSIT_AMOUNT_COMPLETED_DERIVED = "deposit_amount_completed_derived";

        public static final String TOTAL_PAID_IN_ADVANCE_DERIVED = "total_paid_in_advance_derived";

        public static final String TOTAL_PAID_LATE_DERIVED = "total_paid_late_derived";

        public static final String COMPLETED_DERIVED = "completed_derived";

        public static final String OBLIGATIONS_MET_ON_DATE = "obligations_met_on_date";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String CREATED_DATE = "created_date";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

    }

}
