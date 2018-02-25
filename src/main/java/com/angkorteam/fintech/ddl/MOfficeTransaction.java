package com.angkorteam.fintech.ddl;

public interface MOfficeTransaction {

    public static final String NAME = "m_office_transaction";

    public interface Field {

        public static final String ID = "id";

        public static final String FROM_OFFICE_ID = "from_office_id";

        public static final String TO_OFFICE_ID = "to_office_id";

        public static final String CURRENCY_CODE = "currency_code";

        public static final String CURRENCY_DIGITS = "currency_digits";

        public static final String TRANSACTION_AMOUNT = "transaction_amount";

        public static final String TRANSACTION_DATE = "transaction_date";

        public static final String DESCRIPTION = "description";

    }

}
