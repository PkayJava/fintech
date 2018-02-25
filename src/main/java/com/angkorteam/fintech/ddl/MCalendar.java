package com.angkorteam.fintech.ddl;

public interface MCalendar {

    public static final String NAME = "m_calendar";

    public interface Field {

        public static final String ID = "id";

        public static final String TITLE = "title";

        public static final String DESCRIPTION = "description";

        public static final String LOCATION = "location";

        public static final String START_DATE = "start_date";

        public static final String END_DATE = "end_date";

        public static final String DURATION = "duration";

        public static final String CALENDAR_TYPE_ENUM = "calendar_type_enum";

        public static final String REPEATING = "repeating";

        public static final String RECURRENCE = "recurrence";

        public static final String REMIND_BY_ENUM = "remind_by_enum";

        public static final String FIRST_REMINDER = "first_reminder";

        public static final String SECOND_REMINDER = "second_reminder";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

        public static final String CREATED_DATE = "created_date";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

        public static final String MEETING_TIME = "meeting_time";

    }

}
