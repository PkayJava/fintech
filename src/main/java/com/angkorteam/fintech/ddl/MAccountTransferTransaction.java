package com.angkorteam.fintech.ddl;

public interface MAccountTransferTransaction {

    public static final String NAME = "m_account_transfer_transaction";

    public interface Field {

        public static final String ID = "id";

        public static final String ACCOUNT_TRANSFER_DETAILS_ID = "account_transfer_details_id";

        public static final String FROM_SAVINGS_TRANSACTION_ID = "from_savings_transaction_id";

        public static final String FROM_LOAN_TRANSACTION_ID = "from_loan_transaction_id";

        public static final String TO_SAVINGS_TRANSACTION_ID = "to_savings_transaction_id";

        public static final String TO_LOAN_TRANSACTION_ID = "to_loan_transaction_id";

        public static final String IS_REVERSED = "is_reversed";

        public static final String TRANSACTION_DATE = "transaction_date";

        public static final String CURRENCY_CODE = "currency_code";

        public static final String CURRENCY_DIGITS = "currency_digits";

        public static final String CURRENCY_MULTIPLES_OF = "currency_multiplesof";

        public static final String AMOUNT = "amount";

        public static final String DESCRIPTION = "description";

    }

}
