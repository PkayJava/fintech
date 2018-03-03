package com.angkorteam.fintech.ddl;

public interface MLoanTransaction {

    public static final String NAME = "m_loan_transaction";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_ID = "loan_id";

        public static final String OFFICE_ID = "office_id";

        public static final String PAYMENT_DETAIL_ID = "payment_detail_id";

        public static final String IS_REVERSED = "is_reversed";

        public static final String EXTERNAL_ID = "external_id";

        public static final String TRANSACTION_TYPE_ENUM = "transaction_type_enum";

        public static final String TRANSACTION_DATE = "transaction_date";

        public static final String AMOUNT = "amount";

        public static final String PRINCIPAL_PORTION_DERIVED = "principal_portion_derived";

        public static final String INTEREST_PORTION_DERIVED = "interest_portion_derived";

        public static final String FEE_CHARGES_PORTION_DERIVED = "fee_charges_portion_derived";

        public static final String PENALTY_CHARGES_PORTION_DERIVED = "penalty_charges_portion_derived";

        public static final String OVER_PAYMENT_PORTION_DERIVED = "overpayment_portion_derived";

        public static final String UNRECOGNIZED_INCOME_PORTION = "unrecognized_income_portion";

        public static final String OUTSTANDING_LOAN_BALANCE_DERIVED = "outstanding_loan_balance_derived";

        public static final String SUBMITTED_ON_DATE = "submitted_on_date";

        public static final String MANUALLY_ADJUSTED_OR_REVERSED = "manually_adjusted_or_reversed";

        public static final String CREATED_DATE = "created_date";

        public static final String APP_USER_ID = "appuser_id";

    }

}
