package com.angkorteam.fintech.ddl;

public interface MLoanRepaymentSchedule {

    public static final String NAME = "m_loan_repayment_schedule";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_ID = "loan_id";

        public static final String FROM_DATE = "fromdate";

        public static final String DUE_DATE = "duedate";

        public static final String INSTALLMENT = "installment";

        public static final String PRINCIPAL_AMOUNT = "principal_amount";

        public static final String PRINCIPAL_COMPLETED_DERIVED = "principal_completed_derived";

        public static final String PRINCIPAL_WRITTEN_OFF_DERIVED = "principal_writtenoff_derived";

        public static final String INTEREST_AMOUNT = "interest_amount";

        public static final String INTEREST_COMPLETED_DERIVED = "interest_completed_derived";

        public static final String INTEREST_WRITTEN_OFF_DERIVED = "interest_writtenoff_derived";

        public static final String INTEREST_WAIVED_DERIVED = "interest_waived_derived";

        public static final String ACCRUAL_INTEREST_DERIVED = "accrual_interest_derived";

        public static final String FEE_CHARGES_AMOUNT = "fee_charges_amount";

        public static final String FEE_CHARGES_COMPLETED_DERIVED = "fee_charges_completed_derived";

        public static final String FEE_CHARGES_WRITTEN_OFF_DERIVED = "fee_charges_writtenoff_derived";

        public static final String FEE_CHARGES_WAIVED_DERIVED = "fee_charges_waived_derived";

        public static final String ACCRUAL_FEE_CHARGES_DERIVED = "accrual_fee_charges_derived";

        public static final String PENALTY_CHARGES_AMOUNT = "penalty_charges_amount";

        public static final String PENALTY_CHARGES_COMPLETED_DERIVED = "penalty_charges_completed_derived";

        public static final String PENALTY_CHARGES_WRITTEN_OFF_DERIVED = "penalty_charges_writtenoff_derived";

        public static final String PENALTY_CHARGES_WAIVED_DERIVED = "penalty_charges_waived_derived";

        public static final String ACCRUAL_PENALTY_CHARGES_DERIVED = "accrual_penalty_charges_derived";

        public static final String TOTAL_PAID_IN_ADVANCE_DERIVED = "total_paid_in_advance_derived";

        public static final String TOTAL_PAID_LATE_DERIVED = "total_paid_late_derived";

        public static final String COMPLETED_DERIVED = "completed_derived";

        public static final String OBLIGATIONS_MET_ON_DATE = "obligations_met_on_date";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String CREATED_DATE = "created_date";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

        public static final String RECALCULATED_INTEREST_COMPONENT = "recalculated_interest_component";

    }

}
