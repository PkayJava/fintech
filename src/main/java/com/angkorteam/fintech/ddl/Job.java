package com.angkorteam.fintech.ddl;

public interface Job {

    public static final String NAME = "job";

    public interface Field {

        public static final String ID = "id";

        public static final String NAME = "name";

        public static final String DISPLAY_NAME = "display_name";

        public static final String CRON_EXPRESSION = "cron_expression";

        public static final String CREATE_TIME = "create_time";

        public static final String TASK_PRIORITY = "task_priority";

        public static final String GROUP_NAME = "group_name";

        public static final String PREVIOUS_RUN_START_TIME = "previous_run_start_time";

        public static final String NEXT_RUN_TIME = "next_run_time";

        public static final String JOB_KEY = "job_key";

        public static final String INITIALIZING_ERROR_LOG = "initializing_errorlog";

        public static final String IS_ACTIVE = "is_active";

        public static final String CURRENTLY_RUNNING = "currently_running";

        public static final String UPDATES_ALLOWED = "updates_allowed";

        public static final String SCHEDULER_GROUP = "scheduler_group";

        public static final String IS_MISFIRED = "is_misfired";

    }

}
