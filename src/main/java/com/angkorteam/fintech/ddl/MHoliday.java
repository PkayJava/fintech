package com.angkorteam.fintech.ddl;

public interface MHoliday {

    public static final String NAME = "m_holiday";

    public interface Field {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String FROM_DATE = "from_date";

        public static final String TO_DATE = "to_date";

        public static final String REPAYMENTS_RESCHEDULED_TO = "repayments_rescheduled_to";

        public static final String STATUS_ENUM = "status_enum";

        public static final String PROCESSED = "processed";

        public static final String DESCRIPTION = "description";

        public static final String RESCHEDULING_TYPE = "rescheduling_type";

    }

}
