package com.angkorteam.fintech.ddl;

public interface MAccountTransferStandingInstructions {

    public static final String NAME = "m_account_transfer_standing_instructions";

    public interface Field {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String ACCOUNT_TRANSFER_DETAILS_ID = "account_transfer_details_id";

        public static final String PRIORITY = "priority";

        public static final String STATUS = "status";

        public static final String INSTRUCTION_TYPE = "instruction_type";

        public static final String AMOUNT = "amount";

        public static final String VALID_FROM = "valid_from";

        public static final String VALID_TILL = "valid_till";

        public static final String RECURRENCE_TYPE = "recurrence_type";

        public static final String RECURRENCE_FREQUENCY = "recurrence_frequency";

        public static final String RECURRENCE_INTERVAL = "recurrence_interval";

        public static final String RECURRENCE_ON_DAY = "recurrence_on_day";

        public static final String RECURRENCE_ON_MONTH = "recurrence_on_month";

        public static final String LAST_RUN_DATE = "last_run_date";

    }

}
