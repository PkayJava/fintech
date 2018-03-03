package com.angkorteam.fintech.ddl;

public interface MWorkingDays {

    public static final String NAME = "m_working_days";

    public interface Field {

        public static final String ID = "id";

        public static final String RECURRENCE = "recurrence";

        public static final String REPAYMENT_RESCHEDULING_ENUM = "repayment_rescheduling_enum";

        public static final String EXTEND_TERM_DAILY_REPAYMENTS = "extend_term_daily_repayments";

        public static final String EXTEND_TERM_HOLIDAY_REPAYMENT = "extend_term_holiday_repayment";

    }

}
