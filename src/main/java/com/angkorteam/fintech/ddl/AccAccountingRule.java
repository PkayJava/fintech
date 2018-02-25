package com.angkorteam.fintech.ddl;

public interface AccAccountingRule {

    public static final String NAME = "acc_accounting_rule";

    public interface Field {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String OFFICE_ID = "office_id";

        public static final String DEBIT_ACCOUNT_ID = "debit_account_id";

        public static final String ALLOW_MULTIPLE_DEBITS = "allow_multiple_debits";

        public static final String CREDIT_ACCOUNT_ID = "credit_account_id";

        public static final String ALLOW_MULTIPLE_CREDITS = "allow_multiple_credits";

        public static final String DESCRIPTION = "description";

    }

}
