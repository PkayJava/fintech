package com.angkorteam.fintech.ddl;

public interface MLoanTransactionRepaymentScheduleMapping {

    public static final String NAME = "m_loan_transaction_repayment_schedule_mapping";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_TRANSACTION_ID = "loan_transaction_id";

        public static final String LOAN_REPAYMENT_SCHEDULE_ID = "loan_repayment_schedule_id";

        public static final String AMOUNT = "amount";

        public static final String PRINCIPAL_PORTION_DERIVED = "principal_portion_derived";

        public static final String INTEREST_PORTION_DERIVED = "interest_portion_derived";

        public static final String FEE_CHARGES_PORTION_DERIVED = "fee_charges_portion_derived";

        public static final String PENALTY_CHARGES_PORTION_DERIVED = "penalty_charges_portion_derived";

    }

}
