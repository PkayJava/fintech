package com.angkorteam.fintech.ddl;

public interface MLoanOverdueInstallmentCharge {

    public static final String NAME = "m_loan_overdue_installment_charge";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_CHARGE_ID = "loan_charge_id";

        public static final String LOAN_SCHEDULE_ID = "loan_schedule_id";

        public static final String FREQUENCY_NUMBER = "frequency_number";

    }

}
