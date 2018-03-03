package com.angkorteam.fintech.ddl;

public interface MLoanArrearsAging {

    public static final String NAME = "m_loan_arrears_aging";

    public interface Field {

        public static final String LOAN_ID = "loan_id";

        public static final String PRINCIPAL_OVERDUE_DERIVED = "principal_overdue_derived";

        public static final String INTEREST_OVERDUE_DERIVED = "interest_overdue_derived";

        public static final String FEE_CHARGES_OVERDUE_DERIVED = "fee_charges_overdue_derived";

        public static final String PENALTY_CHARGES_OVERDUE_DERIVED = "penalty_charges_overdue_derived";

        public static final String TOTAL_OVERDUE_DERIVED = "total_overdue_derived";

        public static final String OVERDUE_SINCE_DATE_DERIVED = "overdue_since_date_derived";

    }

}
