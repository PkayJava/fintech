package com.angkorteam.fintech.ddl;

public interface MReportMailingJob {

    public static final String NAME = "m_report_mailing_job";

    public interface Field {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String DESCRIPTION = "description";

        public static final String START_DATE_TIME = "start_datetime";

        public static final String RECURRENCE = "recurrence";

        public static final String CREATED_DATE = "created_date";

        public static final String CREATED_BY_ID = "createdby_id";

        public static final String LAST_MODIFIED_DATE = "lastmodified_date";

        public static final String LAST_MODIFIED_BY_ID = "lastmodifiedby_id";

        public static final String EMAIL_RECIPIENTS = "email_recipients";

        public static final String EMAIL_SUBJECT = "email_subject";

        public static final String EMAIL_MESSAGE = "email_message";

        public static final String EMAIL_ATTACHMENT_FILE_FORMAT = "email_attachment_file_format";

        public static final String STRETCHY_REPORT_ID = "stretchy_report_id";

        public static final String STRETCHY_REPORT_PARAM_MAP = "stretchy_report_param_map";

        public static final String PREVIOUS_RUN_DATE_TIME = "previous_run_datetime";

        public static final String NEXT_RUN_DATETIME = "next_run_datetime";

        public static final String PREVIOUS_RUN_STATUS = "previous_run_status";

        public static final String PREVIOUS_RUN_ERROR_LOG = "previous_run_error_log";

        public static final String PREVIOUS_RUN_ERROR_MESSAGE = "previous_run_error_message";

        public static final String NUMBER_OF_RUNS = "number_of_runs";

        public static final String IS_ACTIVE = "is_active";

        public static final String IS_DELETED = "is_deleted";

        public static final String RUN_AS_USER_ID = "run_as_userid";

    }

}
