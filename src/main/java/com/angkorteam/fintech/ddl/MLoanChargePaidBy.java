package com.angkorteam.fintech.ddl;

public interface MLoanChargePaidBy {

    public static final String NAME = "m_loan_charge_paid_by";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_TRANSACTION_ID = "loan_transaction_id";

        public static final String LOAN_CHARGE_ID = "loan_charge_id";

        public static final String AMOUNT = "amount";

        public static final String INSTALLMENT_NUMBER = "installment_number";

    }

}
