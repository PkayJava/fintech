package com.angkorteam.fintech.ddl;

public interface JobRunHistory {

    public static final String NAME = "job_run_history";

    public interface Field {

        public static final String ID = "id";

        public static final String JOB_ID = "job_id";

        public static final String VERSION = "version";

        public static final String START_TIME = "start_time";

        public static final String END_TIME = "end_time";

        public static final String STATUS = "status";

        public static final String ERROR_MESSAGE = "error_message";

        public static final String TRIGGER_TYPE = "trigger_type";

        public static final String ERROR_LOG = "error_log";

    }

}
