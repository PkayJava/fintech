package com.angkorteam.fintech.ddl;

public interface MLoanInstallmentCharge {

    public static final String NAME = "m_loan_installment_charge";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_CHARGE_ID = "loan_charge_id";

        public static final String LOAN_SCHEDULE_ID = "loan_schedule_id";

        public static final String DUE_DATE = "due_date";

        public static final String AMOUNT = "amount";

        public static final String AMOUNT_PAID_DERIVED = "amount_paid_derived";

        public static final String AMOUNT_WAIVED_DERIVED = "amount_waived_derived";

        public static final String AMOUNT_WRITTEN_OFF_DERIVED = "amount_writtenoff_derived";

        public static final String AMOUNT_OUTSTANDING_DERIVED = "amount_outstanding_derived";

        public static final String IS_PAID_DERIVED = "is_paid_derived";

        public static final String WAIVED = "waived";

        public static final String AMOUNT_THROUGH_CHARGE_PAYMENT = "amount_through_charge_payment";

    }

}
