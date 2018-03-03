package com.angkorteam.fintech.ddl;

public interface SmsCampaign {

    public static final String NAME = "sms_campaign";

    public interface Field {

        public static final String ID = "id";

        public static final String CAMPAIGN_NAME = "campaign_name";

        public static final String CAMPAIGN_TYPE = "campaign_type";

        public static final String CAMPAIGN_TRIGGER_TYPE = "campaign_trigger_type";

        public static final String REPORT_ID = "report_id";

        public static final String PROVIDER_ID = "provider_id";

        public static final String PARAM_VALUE = "param_value";

        public static final String STATUS_ENUM = "status_enum";

        public static final String MESSAGE = "message";

        public static final String SUBMITTED_ON_DATE = "submittedon_date";

        public static final String SUBMITTED_ON_USER_ID = "submittedon_userid";

        public static final String APPROVED_ON_DATE = "approvedon_date";

        public static final String APPROVED_ON_USER_ID = "approvedon_userid";

        public static final String CLOSED_ON_DATE = "closedon_date";

        public static final String CLOSED_ON_USER_ID = "closedon_userid";

        public static final String RECURRENCE = "recurrence";

        public static final String NEXT_TRIGGER_DATE = "next_trigger_date";

        public static final String LAST_TRIGGER_DATE = "last_trigger_date";

        public static final String RECURRENCE_START_DATE = "recurrence_start_date";

        public static final String IS_VISIBLE = "is_visible";

        public static final String IS_NOTIFICATION = "is_notification";

    }

}
