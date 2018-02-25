package com.angkorteam.fintech.ddl;

public interface MLoanRecalculationDetails {

    public static final String NAME = "m_loan_recalculation_details";

    public interface Field {

        public static final String ID = "id";

        public static final String LOAN_ID = "loan_id";

        public static final String COMPOUND_TYPE_ENUM = "compound_type_enum";

        public static final String RESCHEDULE_STRATEGY_ENUM = "reschedule_strategy_enum";

        public static final String REST_FREQUENCY_TYPE_ENUM = "rest_frequency_type_enum";

        public static final String REST_FREQUENCY_INTERVAL = "rest_frequency_interval";

        public static final String COMPOUNDING_FREQUENCY_TYPE_ENUM = "compounding_frequency_type_enum";

        public static final String COMPOUNDING_FREQUENCY_INTERVAL = "compounding_frequency_interval";

        public static final String REST_FREQUENCY_NTH_DAY_ENUM = "rest_frequency_nth_day_enum";

        public static final String REST_FREQUENCY_ON_DAY = "rest_frequency_on_day";

        public static final String REST_FREQUENCY_WEEKDAY_ENUM = "rest_frequency_weekday_enum";

        public static final String COMPOUNDING_FREQUENCY_NTH_DAY_ENUM = "compounding_frequency_nth_day_enum";

        public static final String COMPOUNDING_FREQUENCY_ON_DAY = "compounding_frequency_on_day";

        public static final String IS_COMPOUNDING_TO_BE_POSTED_AS_TRANSACTION = "is_compounding_to_be_posted_as_transaction";

        public static final String COMPOUNDING_FREQUENCY_WEEKDAY_ENUM = "compounding_frequency_weekday_enum";

        public static final String ALLOW_COMPOUNDING_ON_EOD = "allow_compounding_on_eod";

    }

}
