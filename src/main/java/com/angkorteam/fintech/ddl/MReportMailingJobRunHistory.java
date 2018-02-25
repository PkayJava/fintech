package com.angkorteam.fintech.ddl;

public interface MReportMailingJobRunHistory {

    public static final String NAME = "m_report_mailing_job_run_history";

    public interface Field {

        public static final String ID = "id";

        public static final String JOB_ID = "job_id";

        public static final String START_DATE_TIME = "start_datetime";

        public static final String END_DATE_TIME = "end_datetime";

        public static final String STATUS = "status";

        public static final String ERROR_MESSAGE = "error_message";

        public static final String ERROR_LOG = "error_log";

    }

}
