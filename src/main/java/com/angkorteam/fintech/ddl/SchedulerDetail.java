package com.angkorteam.fintech.ddl;

public interface SchedulerDetail {

    public static final String NAME = "scheduler_detail";

    public interface Field {

        public static final String ID = "id";

        public static final String IS_SUSPENDED = "is_suspended";

        public static final String EXECUTE_MISFIRED_JOBS = "execute_misfired_jobs";

        public static final String RESET_SCHEDULER_ON_BOOTUP = "reset_scheduler_on_bootup";

    }

}
