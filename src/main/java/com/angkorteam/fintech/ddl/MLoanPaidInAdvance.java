package com.angkorteam.fintech.ddl;

public interface MLoanPaidInAdvance {

    public static final String NAME = "m_loan_paid_in_advance";

    public interface Field {

        public static final String LOAN_ID = "loan_id";

        public static final String PRINCIPAL_IN_ADVANCE_DERIVED = "principal_in_advance_derived";

        public static final String INTEREST_IN_ADVANCE_DERIVED = "interest_in_advance_derived";

        public static final String FEE_CHARGES_IN_ADVANCE_DERIVED = "fee_charges_in_advance_derived";

        public static final String PENALTY_CHARGES_IN_ADVANCE_DERIVED = "penalty_charges_in_advance_derived";

        public static final String TOTAL_IN_ADVANCE_DERIVED = "total_in_advance_derived";

    }

}
