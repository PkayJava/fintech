package com.angkorteam.fintech.meta.tenant;

import com.angkorteam.fintech.meta.AbstractTable;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class Job extends AbstractTable {

    public final Column ID;

    public final Column NAME;

    public final Column DISPLAY_NAME;

    public final Column CRON_EXPRESSION;

    public final Column CREATE_TIME;

    public final Column TASK_PRIORITY;

    public final Column GROUP_NAME;

    public final Column PREVIOUS_RUN_START_TIME;

    public final Column NEXT_RUN_TIME;

    public final Column JOB_KEY;

    public final Column INITIALIZING_ERROR_LOG;

    public final Column ACTIVE;

    public final Column CURRENTLY_RUNNING;

    public final Column UPDATE_ALLOWED;

    public final Column SCHEDULER_GROUP;

    public final Column MISFIRED;

    public static Job staticInitialize(DataContext dataContext) {
        return new Job(dataContext);
    }

    private Job(DataContext dataContext) {
        super(dataContext, "job");
        this.ID = getInternalColumnByName("id");
        this.NAME = getInternalColumnByName("name");
        this.DISPLAY_NAME = getInternalColumnByName("display_name");
        this.CRON_EXPRESSION = getInternalColumnByName("cron_expression");
        this.CREATE_TIME = getInternalColumnByName("create_time");
        this.TASK_PRIORITY = getInternalColumnByName("task_priority");
        this.GROUP_NAME = getInternalColumnByName("group_name");
        this.PREVIOUS_RUN_START_TIME = getInternalColumnByName("previous_run_start_time");
        this.NEXT_RUN_TIME = getInternalColumnByName("next_run_time");
        this.JOB_KEY = getInternalColumnByName("job_key");
        this.INITIALIZING_ERROR_LOG = getInternalColumnByName("initializing_errorlog");
        this.ACTIVE = getInternalColumnByName("is_active");
        this.CURRENTLY_RUNNING = getInternalColumnByName("currently_running");
        this.UPDATE_ALLOWED = getInternalColumnByName("updates_allowed");
        this.SCHEDULER_GROUP = getInternalColumnByName("scheduler_group");
        this.MISFIRED = getInternalColumnByName("is_misfired");
    }

}
