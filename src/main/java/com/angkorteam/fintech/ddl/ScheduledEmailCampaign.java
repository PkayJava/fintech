package com.angkorteam.fintech.ddl;

public interface ScheduledEmailCampaign {

    public static final String NAME = "scheduled_email_campaign";

    public interface Field {

        public static final String ID = "id";

        public static final String CAMPAIGN_NAME = "campaign_name";

        public static final String CAMPAIGN_TYPE = "campaign_type";

        public static final String BUSINESS_RULE_ID = "businessRule_id";

        public static final String PARAM_VALUE = "param_value";

        public static final String STATUS_ENUM = "status_enum";

        public static final String EMAIL_SUBJECT = "email_subject";

        public static final String EMAIL_MESSAGE = "email_message";

        public static final String EMAIL_ATTACHMENT_FILE_FORMAT = "email_attachment_file_format";

        public static final String STRETCHY_REPORT_ID = "stretchy_report_id";

        public static final String STRETCHY_REPORT_PARAM_MAP = "stretchy_report_param_map";

        public static final String CLOSED_ON_DATE = "closedon_date";

        public static final String CLOSED_ON_USERID = "closedon_userid";

        public static final String SUBMITTED_ON_DATE = "submittedon_date";

        public static final String SUBMITTED_ON_USER_ID = "submittedon_userid";

        public static final String APPROVED_ON_DATE = "approvedon_date";

        public static final String APPROVED_ON_USER_ID = "approvedon_userid";

        public static final String RECURRENCE = "recurrence";

        public static final String NEXT_TRIGGER_DATE = "next_trigger_date";

        public static final String LAST_TRIGGER_DATE = "last_trigger_date";

        public static final String RECURRENCE_START_DATE = "recurrence_start_date";

        public static final String IS_VISIBLE = "is_visible";

        public static final String PREVIOUS_RUN_ERROR_LOG = "previous_run_error_log";

        public static final String PREVIOUS_RUN_ERROR_MESSAGE = "previous_run_error_message";

        public static final String PREVIOUS_RUN_STATUS = "previous_run_status";

    }

}
